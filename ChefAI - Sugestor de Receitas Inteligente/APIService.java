import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Serviço responsável pela comunicação com a API Gemini da Google.
 * Implementa fallback automático para dados locais se a API falhar.
 */
public class APIService {
    
    /**
     * Método principal para buscar receitas.
     * Tenta a API primeiro, se falhar usa receitas locais.
     */
    public ArrayList<Receita> buscarReceitas(Usuario usuario) {
        System.out.println("🔗 Conectando com API Gemini...");
        
        try {
            // 1. Verificar se temos chave API configurada
            String apiKey = ConfiguracaoAPI.getApiKey();
            if (apiKey == null || apiKey.isEmpty() || apiKey.contains("SUA_CHAVE")) {
                System.out.println("⚠️  Chave API não configurada. Usando modo local.");
                return criarReceitasLocais(usuario);
            }
            
            // 2. Tentar chamar a API
            System.out.println("🌐 Tentando conectar com Gemini API...");
            String respostaJson = fazerRequisicaoAPI(usuario, apiKey);
            
            // 3. Processar resposta
            ArrayList<Receita> receitas = processarRespostaAPI(respostaJson);
            
            if (receitas.isEmpty()) {
                System.out.println("📭 API não retornou receitas válidas. Usando modo local.");
                return criarReceitasLocais(usuario);
            }
            
            System.out.println("✅ " + receitas.size() + " receitas obtidas da API!");
            return receitas;
            
        } catch (Exception e) {
            System.out.println("❌ Erro na API: " + e.getMessage());
            System.out.println("🔄 Ativando fallback para receitas locais...");
            return criarReceitasLocais(usuario);
        }
    }
    
    /**
     * Faz a requisição HTTP para a API Gemini.
     */
    private String fazerRequisicaoAPI(Usuario usuario, String apiKey) throws Exception {
        String urlString = ConfiguracaoAPI.getApiUrl() + "?key=" + apiKey;
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        // Configurar conexão
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        conn.setConnectTimeout(15000); // 15 segundos timeout
        conn.setReadTimeout(15000);
        
        // Criar prompt personalizado
        String prompt = criarPromptPersonalizado(usuario);
        
        // Corpo da requisição no formato Gemini
        String requestBody = String.format(
            "{\"contents\":[{\"parts\":[{\"text\":\"%s\"}]}], " +
            "\"generationConfig\":{\"temperature\":0.7,\"maxOutputTokens\":2000}}",
            escapeJson(prompt)
        );
        
        System.out.println("📤 Enviando requisição para API...");
        
        // Enviar dados
        OutputStream os = conn.getOutputStream();
        os.write(requestBody.getBytes("UTF-8"));
        os.flush();
        os.close();
        
        // Verificar resposta
        int responseCode = conn.getResponseCode();
        System.out.println("📥 Código HTTP: " + responseCode);
        
        if (responseCode != 200) {
            // Tentar ler mensagem de erro
            try {
                BufferedReader errorReader = new BufferedReader(
                    new InputStreamReader(conn.getErrorStream()));
                StringBuilder errorResponse = new StringBuilder();
                String line;
                while ((line = errorReader.readLine()) != null) {
                    errorResponse.append(line);
                }
                errorReader.close();
                System.out.println("Erro da API: " + errorResponse.toString());
            } catch (Exception e) {
                // Ignorar erro ao ler erro
            }
            throw new Exception("API retornou erro HTTP " + responseCode);
        }
        
        // Ler resposta bem-sucedida
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        br.close();
        
        return response.toString();
    }
    
    /**
     * Cria um prompt bem estruturado para a API Gemini.
     */
    private String criarPromptPersonalizado(Usuario usuario) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("Você é um chef de cozinha brasileiro especializado em receitas rápidas.\n\n");
        
        sb.append("POR FAVOR, gere 3 receitas que:\n");
        sb.append("1. Usem principalmente estes ingredientes disponíveis:\n");
        
        for (Ingrediente ing : usuario.getIngredientesDisponiveis()) {
            sb.append("   - ").append(ing.getQuantidade()).append(" ").append(ing.getNome()).append("\n");
        }
        
        sb.append("\n2. Sejam rápidas (máximo 30 minutos de preparo)\n");
        sb.append("3. Sejam realistas para cozinha doméstica\n");
        
        // Adicionar restrições alimentares
        if (usuario.isVegetariano()) {
            sb.append("4. Sejam VEGETARIANAS (sem carne, peixe ou frango)\n");
        }
        if (usuario.isSemLactose()) {
            sb.append("5. Sejam SEM LACTOSE\n");
        }
        if (usuario.isSemGluten()) {
            sb.append("6. Sejam SEM GLÚTEN\n");
        }
        
        sb.append("\nFORMATO EXATO DE RESPOSTA (IMPORTANTE!):\n");
        sb.append("Para cada receita, forneça nestas linhas EXATAS:\n");
        sb.append("NOME_RECEITA: [nome completo da receita]\n");
        sb.append("TEMPO: [tempo em minutos]\n");
        sb.append("VEGETARIANA: [SIM ou NÃO]\n");
        sb.append("INGREDIENTES:\n");
        sb.append("[quantidade] [nome do ingrediente]\n");
        sb.append("[quantidade] [nome do ingrediente]\n");
        sb.append("PASSOS:\n");
        sb.append("1. [primeiro passo]\n");
        sb.append("2. [segundo passo]\n");
        sb.append("---FIM_RECEITA---\n");
        
        sb.append("\nExemplo:\n");
        sb.append("NOME_RECEITA: Omelete Simples\n");
        sb.append("TEMPO: 10\n");
        sb.append("VEGETARIANA: SIM\n");
        sb.append("INGREDIENTES:\n");
        sb.append("3 ovo\n");
        sb.append("50 queijo\n");
        sb.append("1 sal\n");
        sb.append("PASSOS:\n");
        sb.append("1. Bata os ovos em uma tigela\n");
        sb.append("2. Aqueça uma frigideira antiaderente\n");
        sb.append("3. Cozinhe por 5 minutos\n");
        sb.append("---FIM_RECEITA---\n");
        
        sb.append("\nRetorne APENAS as 3 receitas neste formato, sem explicações adicionais.");
        
        return sb.toString();
    }
    
    /**
     * Processa a resposta JSON da API e converte para objetos Receita.
     */
    private ArrayList<Receita> processarRespostaAPI(String respostaJson) {
        ArrayList<Receita> receitas = new ArrayList<>();
        
        try {
            System.out.println("🔍 Processando resposta da API...");
            
            // Extrair o texto da resposta JSON
            String textoResposta = extrairTextoDaResposta(respostaJson);
            
            if (textoResposta == null || textoResposta.isEmpty()) {
                System.out.println("Resposta da API vazia ou inválida.");
                return receitas;
            }
            
            // Dividir por "---FIM_RECEITA---"
            String[] partesReceitas = textoResposta.split("---FIM_RECEITA---");
            
            for (String parte : partesReceitas) {
                Receita receita = parseReceita(parte.trim());
                if (receita != null) {
                    receitas.add(receita);
                    System.out.println("  ✅ Receita extraída: " + receita.getNome());
                }
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao processar resposta da API: " + e.getMessage());
        }
        
        return receitas;
    }
    
    /**
     * Extrai o texto da resposta JSON da Gemini.
     */
    private String extrairTextoDaResposta(String json) {
        try {
            // Procurar por "text": no JSON
            int startIndex = json.indexOf("\"text\":\"");
            if (startIndex == -1) return null;
            
            startIndex += 8; // Tamanho de "\"text\":\""
            int endIndex = json.indexOf("\"", startIndex);
            
            if (endIndex == -1) return null;
            
            String texto = json.substring(startIndex, endIndex);
            
            // Remover escapes
            texto = texto.replace("\\n", "\n")
                        .replace("\\\"", "\"")
                        .replace("\\\\", "\\");
            
            return texto;
            
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Converte um bloco de texto no formato especificado para objeto Receita.
     */
    private Receita parseReceita(String textoReceita) {
        try {
            String nome = null;
            int tempo = 15; // default
            boolean vegetariana = false;
            ArrayList<Ingrediente> ingredientes = new ArrayList<>();
            ArrayList<String> passos = new ArrayList<>();
            
            String[] linhas = textoReceita.split("\n");
            String secaoAtual = null; // "INGREDIENTES" ou "PASSOS"
            
            for (String linha : linhas) {
                linha = linha.trim();
                if (linha.isEmpty()) continue;
                
                // Identificar seções
                if (linha.startsWith("NOME_RECEITA:")) {
                    nome = linha.substring("NOME_RECEITA:".length()).trim();
                }
                else if (linha.startsWith("TEMPO:")) {
                    try {
                        String tempoStr = linha.substring("TEMPO:".length()).trim();
                        tempo = Integer.parseInt(tempoStr.replaceAll("[^0-9]", ""));
                    } catch (NumberFormatException e) {
                        tempo = 15; // default se erro
                    }
                }
                else if (linha.startsWith("VEGETARIANA:")) {
                    String vegStr = linha.substring("VEGETARIANA:".length()).trim();
                    vegetariana = vegStr.equalsIgnoreCase("SIM");
                }
                else if (linha.equals("INGREDIENTES:")) {
                    secaoAtual = "INGREDIENTES";
                }
                else if (linha.equals("PASSOS:")) {
                    secaoAtual = "PASSOS";
                }
                else if (secaoAtual != null) {
                    if (secaoAtual.equals("INGREDIENTES")) {
                        // Formato: "quantidade nome"
                        String[] partes = linha.split(" ", 2);
                        if (partes.length == 2) {
                            try {
                                int quantidade = Integer.parseInt(partes[0].trim());
                                String nomeIng = partes[1].trim();
                                ingredientes.add(new Ingrediente(nomeIng, quantidade));
                            } catch (NumberFormatException e) {
                                // Ignorar linha inválida
                            }
                        }
                    }
                    else if (secaoAtual.equals("PASSOS")) {
                        // Formato: "1. passo" ou "1) passo"
                        if (linha.matches("^\\d+[\\).].*") || linha.matches("^\\d+\\..*")) {
                            // Remover número no início
                            String passo = linha.replaceFirst("^\\d+[\\).]\\s*", "")
                                              .replaceFirst("^\\d+\\.\\s*", "");
                            passos.add(passo.trim());
                        }
                    }
                }
            }
            
            // Validar receita
            if (nome == null || nome.isEmpty() || ingredientes.isEmpty()) {
                return null;
            }
            
            // Criar objeto Receita
            Receita receita = new Receita(nome, tempo, vegetariana);
            for (Ingrediente ing : ingredientes) {
                receita.adicionarIngrediente(ing);
            }
            for (String passo : passos) {
                receita.adicionarPasso(passo);
            }
            
            return receita;
            
        } catch (Exception e) {
            System.out.println("Erro ao parsear receita: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Cria receitas locais como fallback quando a API falha.
     */
    private ArrayList<Receita> criarReceitasLocais(Usuario usuario) {
        System.out.println("🏠 Criando receitas locais de exemplo...");
        
        ArrayList<Receita> receitas = new ArrayList<>();
        
        // Receita 1 - Omelete
        Receita r1 = new Receita("Omelete de Queijo", 12, false);
        r1.adicionarIngrediente(new Ingrediente("ovo", 3));
        r1.adicionarIngrediente(new Ingrediente("queijo", 100));
        r1.adicionarIngrediente(new Ingrediente("sal", 1));
        r1.adicionarPasso("Bata os ovos em uma tigela");
        r1.adicionarPasso("Adicione queijo ralado e sal");
        r1.adicionarPasso("Aqueça uma frigideira antiaderente");
        r1.adicionarPasso("Despeje a mistura e cozinhe por 5-7 minutos");
        
        // Receita 2 - Panqueca
        Receita r2 = new Receita("Panquecas Simples", 18, true);
        r2.adicionarIngrediente(new Ingrediente("farinha", 200));
        r2.adicionarIngrediente(new Ingrediente("leite", 250));
        r2.adicionarIngrediente(new Ingrediente("ovo", 2));
        r2.adicionarIngrediente(new Ingrediente("açúcar", 30));
        r2.adicionarPasso("Misture farinha e açúcar em uma tigela");
        r2.adicionarPasso("Adicione ovos e leite aos poucos");
        r2.adicionarPasso("Bata até obter uma massa homogênea");
        r2.adicionarPasso("Frite em frigideira quente por 2-3 minutos cada lado");
        
        // Receita 3 - Sanduíche
        Receita r3 = new Receita("Sanduíche Quente", 8, false);
        r3.adicionarIngrediente(new Ingrediente("pão", 2));
        r3.adicionarIngrediente(new Ingrediente("queijo", 2));
        r3.adicionarPasso("Coloque fatias de queijo entre o pão");
        r3.adicionarPasso("Aqueça em frigideira por 2-3 minutos");
        r3.adicionarPasso("Vire e aqueça do outro lado");
        r3.adicionarPasso("Sirva quente");
        
        // Receita 4 - Ovo mexido (para usuários vegetarianos)
        Receita r4 = new Receita("Ovo Mexido Cremoso", 10, true);
        r4.adicionarIngrediente(new Ingrediente("ovo", 3));
        r4.adicionarIngrediente(new Ingrediente("leite", 2));
        r4.adicionarIngrediente(new Ingrediente("sal", 1));
        r4.adicionarPasso("Bata os ovos com leite e sal");
        r4.adicionarPasso("Derreta manteiga em frigideira em fogo baixo");
        r4.adicionarPasso("Adicione os ovos e mexa constantemente");
        r4.adicionarPasso("Cozinhe até ficar cremoso");
        
        // Selecionar receitas baseadas nas preferências do usuário
        for (Receita receita : new Receita[]{r1, r2, r3, r4}) {
            if (receitas.size() >= 3) break;
            
            // Verificar se atende às preferências
            boolean adequada = true;
            
            if (usuario.isVegetariano() && !receita.isVegetariana()) {
                adequada = false;
            }
            
            // Verificar compatibilidade básica (pelo menos 1 ingrediente em comum)
            if (adequada) {
                int ingredientesComuns = 0;
                for (Ingrediente ingReceita : receita.getIngredientes()) {
                    if (usuario.temIngrediente(ingReceita.getNome())) {
                        ingredientesComuns++;
                    }
                }
                
                if (ingredientesComuns > 0) {
                    receitas.add(receita);
                }
            }
        }
        
        // Se não encontrou suficientes, adiciona algumas padrão
        while (receitas.size() < 3) {
            receitas.add(r1);
            if (receitas.size() < 3) receitas.add(r2);
            if (receitas.size() < 3) receitas.add(r3);
            break;
        }
        
        return receitas;
    }
    
    /**
     * Escapa caracteres especiais para JSON.
     */
    private String escapeJson(String texto) {
        return texto.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
    
    /**
     * Método de teste para verificar se a API está funcionando.
     */
    public void testarConexaoAPI() {
        System.out.println("=== TESTE DE CONEXÃO COM API ===\n");
        
        String apiKey = ConfiguracaoAPI.getApiKey();
        
        if (apiKey == null || apiKey.isEmpty() || apiKey.contains("SUA_CHAVE")) {
            System.out.println("❌ Chave API não configurada.");
            System.out.println("Edite config.properties com sua chave Gemini");
            return;
        }
        
        System.out.println("Chave encontrada: " + apiKey.substring(0, 15) + "...");
        
        try {
            // Teste simples de conexão
            String urlString = ConfiguracaoAPI.getApiUrl() + "?key=" + apiKey;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            
            int responseCode = conn.getResponseCode();
            System.out.println("Resposta HTTP: " + responseCode + 
                (responseCode == 200 ? " ✅" : " ⚠️"));
            
            if (responseCode == 200) {
                System.out.println("✅ API Gemini está acessível!");
            } else {
                System.out.println("⚠️  API respondeu com código " + responseCode);
                System.out.println("Isso pode ser normal para requisições GET.");
            }
            
        } catch (Exception e) {
            System.out.println("❌ Erro na conexão: " + e.getMessage());
        }
    }
}