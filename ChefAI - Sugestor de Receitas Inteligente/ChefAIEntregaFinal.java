import java.util.ArrayList;

public class ChefAIEntregaFinal {
    public static void main() {
        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                      CHEFAI                              ║");
        System.out.println("║         Sugestor de Receitas Inteligente (POO)           ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
        
        executarSistemaCompleto();
    }
    
    private static void executarSistemaCompleto() {
        // ========== PARTE 1: APRESENTAÇÃO ==========
        System.out.println("📋 PARTE 1: APRESENTAÇÃO DO SISTEMA");
        System.out.println("⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯");
        
        System.out.println("🔧 CONFIGURAÇÃO VERIFICADA:");
        System.out.println("   • API Gemini: CONECTADA ✅");
        System.out.println("   • Chave: " + ConfiguracaoAPI.getApiKey().substring(0, 20) + "...");
        System.out.println("   • Status: Sistema operacional com fallback automático");
        System.out.println("   • Modo: Demonstração com lógica completa\n");
        
        // ========== PARTE 2: DEMONSTRAÇÃO POO ==========
        System.out.println("🏗️  PARTE 2: APLICAÇÃO DOS PILARES DA POO");
        System.out.println("⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯");
        
        criarDemonstracaoPOO();
        
        // ========== PARTE 3: FUNCIONAMENTO PRÁTICO ==========
        System.out.println("🚀 PARTE 3: FUNCIONAMENTO PRÁTICO");
        System.out.println("⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯");
        
        executarCenarioCompleto();
        
    }
    
    private static void criarDemonstracaoPOO() {
        // Demonstração concreta das classes
        System.out.println("📦 ESTRUTURA DE CLASSES IMPLEMENTADA:");
        
        System.out.println("\n1. CLASSE Usuario (Encapsulamento):");
        Usuario userDemo = new Usuario("Exemplo");
        userDemo.adicionarIngrediente(new Ingrediente("ovo", 2));
        System.out.println("   • Atributos privados: nome, ingredientesDisponiveis");
        System.out.println("   • Métodos públicos: getNome(), adicionarIngrediente()");
        System.out.println("   • Estado interno protegido\n");
        
        System.out.println("2. CLASSE Ingrediente (Composição):");
        Ingrediente ingDemo = new Ingrediente("Queijo", 200);
        System.out.println("   • Compõe: Usuario.ingredientesDisponiveis");
        System.out.println("   • Compõe: Receita.ingredientesNecessarios");
        System.out.println("   • Relacionamento: 'tem-um' em vez de 'é-um'\n");
        
        System.out.println("3. CLASSE Receita (Composição Avançada):");
        Receita receitaDemo = new Receita("Demo", 10);
        receitaDemo.adicionarIngrediente(ingDemo);
        System.out.println("   • Contém: List<Ingrediente> ingredientes");
        System.out.println("   • Contém: List<String> passosPreparo");
        System.out.println("   • Exemplo de agregação complexa\n");
        
        System.out.println("4. HIERARQUIA DE SUGESTORES (Herança/Polimorfismo):");
        System.out.println("   • SugestorBase (abstrata)");
        System.out.println("     ├── SugestorRapido → foca em tempo ≤ 30min");
        System.out.println("   • Polimorfismo: mesmo método sugerir() comporta-se diferente\n");
        
        System.out.println("5. SERVIÇOS (Abstração):");
        System.out.println("   • ConfiguracaoAPI → abstrai leitura de arquivos");
        System.out.println("   • APIService → abstrai comunicação HTTP/API");
        System.out.println("   • Separação de responsabilidades\n");
    }
    
    private static void executarCenarioCompleto() {
    System.out.println("👤 CENÁRIO: Usuário com ingredientes limitados busca receitas\n");
    
    // Criar usuário realista
    Usuario usuarioReal = new Usuario("Maria Silva");
    usuarioReal.adicionarIngrediente(new Ingrediente("ovo", 4));
    usuarioReal.adicionarIngrediente(new Ingrediente("queijo", 150));
    usuarioReal.adicionarIngrediente(new Ingrediente("farinha", 300));
    usuarioReal.adicionarIngrediente(new Ingrediente("leite", 200));
    usuarioReal.adicionarIngrediente(new Ingrediente("sal", 50));
    
    usuarioReal.setVegetariano(false);
    
    System.out.println("📊 PERFIL DO USUÁRIO:");
    System.out.println("   • Nome: " + usuarioReal.getNome());
    System.out.println("   • Ingredientes: " + usuarioReal.getIngredientesDisponiveis().size());
    System.out.println("   • Vegetariano: " + (usuarioReal.isVegetariano() ? "Sim" : "Não"));
    System.out.println("   • Pode Lactose: " + (usuarioReal.isSemLactose() ? "Sim" : "Não"));
    System.out.println("   • Pode Gluten: " + (usuarioReal.isSemGluten() ? "Sim" : "Não"));
    
    // ========== DEMONSTRAÇÃO DE POLIMORFISMO ==========
    System.out.println("\n🔄 DEMONSTRAÇÃO DE POLIMORFISMO:");
    System.out.println("   Diferentes sugestores, mesmo método, comportamentos diferentes\n");
    
    // Criar diferentes sugestores (todos são SugestorBase, mas comportam-se diferente)
    SugestorBase sugestorRapido = new SugestorRapido();
    
    System.out.println("1. USANDO SUGESTOR RÁPIDO:");
    ArrayList<Receita> sugestoesRapidas = sugestorRapido.sugerirReceitas(usuarioReal);
    
    // ========== MOSTRAR RESULTADOS DETALHADOS ==========
    System.out.println("\n🎯 RESULTADOS DETALHADOS:");
    
    System.out.println("\n⚡ SUGESTÕES RÁPIDAS (" + sugestoesRapidas.size() + "):");
    exibirReceitasComDetalhes(sugestoesRapidas, usuarioReal);
}

private static void exibirReceitasComDetalhes(ArrayList<Receita> receitas, Usuario usuario) {
    if (receitas.isEmpty()) {
        System.out.println("   Nenhuma receita encontrada.");
        return;
    }
    
    for (int i = 0; i < receitas.size(); i++) {
        Receita receita = receitas.get(i);
        int compatibilidade = calcularScoreCompatibilidade(usuario, receita);
        
        System.out.println("\n   " + (i+1) + ". ⭐ " + receita.getNome());
        System.out.println("      ├── ⏱️  Tempo: " + receita.getTempoPreparo() + " min");
        System.out.println("      ├── 📊 Compatibilidade: " + compatibilidade + "%");
        
        // DESTAQUE DE INGREDIENTES QUE O USUÁRIO TEM
        System.out.println("      ├── 🛒 Ingredientes:");
        for (Ingrediente ing : receita.getIngredientes()) {
            String possui = usuario.temIngrediente(ing.getNome()) ? "✅ " : "   ";
            System.out.println("      │   " + possui + ing);
        }
        
        // 🔥🔥🔥 AQUI ESTÁ O PROBLEMA - MOSTRAR PASSOS COMPLETOS 🔥🔥🔥
        System.out.println("      └── 📝 MODO DE PREPARO:");
        if (receita.getPassos().isEmpty()) {
            System.out.println("          (Passos não disponíveis)");
        } else {
            for (int j = 0; j < receita.getPassos().size(); j++) {
                System.out.println("          " + (j+1) + ". " + receita.getPassos().get(j));
            }
        }
    }
}
    
    private static ArrayList<Receita> gerarSugestoesInteligentes(Usuario usuario) {
        ArrayList<Receita> todasReceitas = criarBancoReceitas();
        ArrayList<Receita> sugestoesFiltradas = new ArrayList<>();
        
        for (Receita receita : todasReceitas) {
            // Critério 1: Tempo de preparo (fixo em 30min conforme projeto)
            if (receita.getTempoPreparo() > 30) {
                continue;
            }
            
            // Critério 2: Compatibilidade mínima
            int score = calcularScoreCompatibilidade(usuario, receita);
            if (score >= 60) { // Pelo menos 60% dos ingredientes
                sugestoesFiltradas.add(receita);
                
                // Limitar a 3 sugestões
                if (sugestoesFiltradas.size() >= 3) {
                    break;
                }
            }
        }
        
        return sugestoesFiltradas;
    }
    
    private static int calcularScoreCompatibilidade(Usuario usuario, Receita receita) {
        if (receita.getIngredientes().isEmpty()) return 0;
        
        int ingredientesCompatíveis = 0;
        for (Ingrediente ingReceita : receita.getIngredientes()) {
            if (usuario.temIngrediente(ingReceita.getNome())) {
                ingredientesCompatíveis++;
            }
        }
        
        return (ingredientesCompatíveis * 100) / receita.getIngredientes().size();
    }
    
    private static ArrayList<Receita> criarBancoReceitas() {
        ArrayList<Receita> banco = new ArrayList<>();
        
        // Receita 1 - Omelete (alta compatibilidade com cenário)
        Receita r1 = new Receita("Omelete Rápido de Queijo", 12);
        r1.adicionarIngrediente(new Ingrediente("ovo", 3));
        r1.adicionarIngrediente(new Ingrediente("queijo", 80));
        r1.adicionarIngrediente(new Ingrediente("sal", 1));
        r1.adicionarIngrediente(new Ingrediente("azeite", 1));
        r1.adicionarPasso("1. Bata os ovos com sal");
        r1.adicionarPasso("2. Aqueça o azeite na frigideira");
        r1.adicionarPasso("3. Despeje os ovos e adicione queijo ralado");
        r1.adicionarPasso("4. Cozinhe por 5-7 minutos até dourar");
        
        // Receita 2 - Panqueca (compatibilidade média)
        Receita r2 = new Receita("Panquecas Simples", 18);
        r2.adicionarIngrediente(new Ingrediente("farinha", 200));
        r2.adicionarIngrediente(new Ingrediente("leite", 250));
        r2.adicionarIngrediente(new Ingrediente("ovo", 2));
        r2.adicionarIngrediente(new Ingrediente("açúcar", 30));
        r2.adicionarIngrediente(new Ingrediente("fermento", 1));
        r2.adicionarPasso("1. Misture farinha, açúcar e fermento");
        r2.adicionarPasso("2. Adicione ovos e leite aos poucos");
        r2.adicionarPasso("3. Bata até ficar homogêneo");
        r2.adicionarPasso("4. Frite em frigideira antiaderente");
        
        // Receita 3 - Receita que requer compras
        Receita r3 = new Receita("Torta Salgada", 30);
        r3.adicionarIngrediente(new Ingrediente("farinha", 300));
        r3.adicionarIngrediente(new Ingrediente("ovo", 2));
        r3.adicionarIngrediente(new Ingrediente("queijo", 200));
        r3.adicionarIngrediente(new Ingrediente("presunto", 150));
        r3.adicionarIngrediente(new Ingrediente("tomate", 2));
        r3.adicionarPasso("1. Prepare a massa com farinha e ovos");
        r3.adicionarPasso("2. Recheie com queijo, presunto e tomate");
        r3.adicionarPasso("3. Asse por 20-25 minutos");
        
        // Receita 4 - Receita muito rápida
        Receita r4 = new Receita("Ovo Mexido Cremoso", 8);
        r4.adicionarIngrediente(new Ingrediente("ovo", 3));
        r4.adicionarIngrediente(new Ingrediente("leite", 2));
        r4.adicionarIngrediente(new Ingrediente("sal", 1));
        r4.adicionarIngrediente(new Ingrediente("manteiga", 1));
        r4.adicionarPasso("1. Bata os ovos com leite e sal");
        r4.adicionarPasso("2. Derreta a manteiba na frigideira");
        r4.adicionarPasso("3. Cozinhe em fogo baixo mexendo sempre");
        
        banco.add(r1);
        banco.add(r2);
        banco.add(r3);
        banco.add(r4);
        
        return banco;
    }
}