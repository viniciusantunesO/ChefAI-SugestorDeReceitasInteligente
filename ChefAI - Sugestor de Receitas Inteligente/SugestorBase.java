import java.util.ArrayList;

/**
 * Classe abstrata base para todos os sugestores de receita.
 * Demonstra HERANÇA e ABSTRAÇÃO na POO.
 * 
 * Todas as subclasses devem implementar o método abstrato sugerirReceitas().
 * Demonstra POLIMORFISMO - cada sugestor implementa de forma diferente.
 */
public abstract class SugestorBase {
    
    // ========== MÉTODO ABSTRATO (POLIMORFISMO) ==========
    
    /**
     * Método abstrato que todas as subclasses DEVEM implementar.
     * Cada Sugestor implementa sua própria lógica de sugestão.
     * 
     * @param usuario O usuário para quem sugerir receitas
     * @return Lista de receitas sugeridas
     */
    public abstract ArrayList<Receita> sugerirReceitas(Usuario usuario);
    
    // ========== MÉTODOS PROTEGIDOS (HERANÇA) ==========
    
    /**
     * Filtra receitas por tempo máximo de preparo.
     * Método PROTEGIDO - acessível apenas para subclasses.
     * 
     * @param receitas Lista de receitas para filtrar
     * @param tempoMaximo Tempo máximo em minutos
     * @return Receitas filtradas
     */
    protected ArrayList<Receita> filtrarPorTempo(ArrayList<Receita> receitas, int tempoMaximo) {
        ArrayList<Receita> filtradas = new ArrayList<>();
        
        for (Receita receita : receitas) {
            if (receita.getTempoPreparo() <= tempoMaximo) {
                filtradas.add(receita);
            }
        }
        
        return filtradas;
    }
    
    /**
     * Filtra receitas vegetarianas.
     * 
     * @param receitas Lista de receitas
     * @return Apenas receitas vegetarianas
     */
    protected ArrayList<Receita> filtrarVegetarianas(ArrayList<Receita> receitas) {
        ArrayList<Receita> vegetarianas = new ArrayList<>();
        
        for (Receita receita : receitas) {
            if (receita.isVegetariana()) {
                vegetarianas.add(receita);
            }
        }
        
        return vegetarianas;
    }
    
    /**
     * Calcula a compatibilidade entre usuário e receita.
     * 
     * @param usuario O usuário
     * @param receita A receita
     * @return Porcentagem de compatibilidade (0-100)
     */
    protected int calcularCompatibilidade(Usuario usuario, Receita receita) {
        if (receita.getIngredientes().isEmpty()) {
            return 0;
        }
        
        int ingredientesCompativeis = 0;
        for (Ingrediente ingReceita : receita.getIngredientes()) {
            if (usuario.temIngrediente(ingReceita.getNome())) {
                ingredientesCompativeis++;
            }
        }
        
        return (ingredientesCompativeis * 100) / receita.getIngredientes().size();
    }
    
    /**
     * Ordena receitas por compatibilidade (maior primeiro).
     * 
     * @param receitas Lista de receitas
     * @param usuario Usuário para cálculo de compatibilidade
     * @return Receitas ordenadas
     */
    protected ArrayList<Receita> ordenarPorCompatibilidade(ArrayList<Receita> receitas, Usuario usuario) {
        ArrayList<Receita> ordenadas = new ArrayList<>(receitas);
        
        ordenadas.sort((r1, r2) -> {
            int comp1 = calcularCompatibilidade(usuario, r1);
            int comp2 = calcularCompatibilidade(usuario, r2);
            return Integer.compare(comp2, comp1); // Decrescente
        });
        
        return ordenadas;
    }
    
    /**
     * Ordena receitas por tempo de preparo (menor primeiro).
     * 
     * @param receitas Lista de receitas
     * @return Receitas ordenadas
     */
    protected ArrayList<Receita> ordenarPorTempo(ArrayList<Receita> receitas) {
        ArrayList<Receita> ordenadas = new ArrayList<>(receitas);
        
        ordenadas.sort((r1, r2) -> {
            return Integer.compare(r1.getTempoPreparo(), r2.getTempoPreparo());
        });
        
        return ordenadas;
    }
    
    /**
     * Limita o número de receitas retornadas.
     * 
     * @param receitas Lista de receitas
     * @param limite Número máximo de receitas
     * @return Receitas limitadas
     */
    protected ArrayList<Receita> limitarReceitas(ArrayList<Receita> receitas, int limite) {
        if (receitas.size() <= limite) {
            return receitas;
        }
        
        ArrayList<Receita> limitadas = new ArrayList<>();
        for (int i = 0; i < limite && i < receitas.size(); i++) {
            limitadas.add(receitas.get(i));
        }
        
        return limitadas;
    }
    
    // ========== BANCO DE RECEITAS (COMPOSIÇÃO) ==========
    
    /**
     * Cria um banco básico de receitas para demonstração.
     * Método CONCRETO que pode ser usado por todas as subclasses.
     * Demonstra COMPOSIÇÃO - Receita contém Ingredientes.
     * 
     * @return Lista de receitas de exemplo
     */
    protected ArrayList<Receita> criarBancoReceitasBasico() {
        ArrayList<Receita> banco = new ArrayList<>();
        
        // Receita 1 - Omelete (NÃO vegetariana)
        Receita omelete = new Receita("Omelete Clássico", 12, false);
        omelete.adicionarIngrediente(new Ingrediente("ovo", 3));
        omelete.adicionarIngrediente(new Ingrediente("queijo", 100));
        omelete.adicionarIngrediente(new Ingrediente("sal", 1));
        omelete.adicionarPasso("Bata os ovos com sal em uma tigela");
        omelete.adicionarPasso("Adicione queijo ralado e misture bem");
        omelete.adicionarPasso("Aqueça uma frigideira antiaderente em fogo médio");
        omelete.adicionarPasso("Despeje a mistura e cozinhe por 5-7 minutos até dourar");
        omelete.adicionarPasso("Vire com cuidado e cozinhe por mais 2 minutos");
        omelete.adicionarPasso("Sirva quente");
        
        // Receita 2 - Panqueca (vegetariana)
        Receita panqueca = new Receita("Panquecas Simples", 18, true);
        panqueca.adicionarIngrediente(new Ingrediente("farinha", 200));
        panqueca.adicionarIngrediente(new Ingrediente("leite", 250));
        panqueca.adicionarIngrediente(new Ingrediente("ovo", 2));
        panqueca.adicionarIngrediente(new Ingrediente("açúcar", 30));
        panqueca.adicionarIngrediente(new Ingrediente("fermento", 1));
        panqueca.adicionarPasso("Misture farinha, açúcar e fermento em uma tigela grande");
        panqueca.adicionarPasso("Adicione os ovos e metade do leite, misturando bem");
        panqueca.adicionarPasso("Incorpore o restante do leite aos poucos até obter massa homogênea");
        panqueca.adicionarPasso("Deixe a massa descansar por 5 minutos");
        panqueca.adicionarPasso("Aqueça uma frigideira antiaderente em fogo médio");
        panqueca.adicionarPasso("Coloque uma concha de massa e espalhe pela frigideira");
        panqueca.adicionarPasso("Cozinhe por 2-3 minutos até formar bolhas, então vire");
        panqueca.adicionarPasso("Cozinhe por mais 1-2 minutos do outro lado");
        panqueca.adicionarPasso("Repita com o restante da massa");
        
        // Receita 3 - Sanduíche (NÃO vegetariana - tem queijo)
        Receita sanduiche = new Receita("Sanduíche Quente", 8, false);
        sanduiche.adicionarIngrediente(new Ingrediente("pão", 2));
        sanduiche.adicionarIngrediente(new Ingrediente("queijo", 2));
        sanduiche.adicionarIngrediente(new Ingrediente("manteiga", 1));
        sanduiche.adicionarPasso("Passe manteiga na parte externa das fatias de pão");
        sanduiche.adicionarPasso("Coloque as fatias de queijo entre as fatias de pão");
        sanduiche.adicionarPasso("Aqueça uma frigideira em fogo médio");
        sanduiche.adicionarPasso("Coloque o sanduíche na frigideira e cozinhe por 2-3 minutos");
        sanduiche.adicionarPasso("Vire cuidadosamente com uma espátula");
        sanduiche.adicionarPasso("Cozinhe por mais 2-3 minutos até dourar e o queijo derreter");
        sanduiche.adicionarPasso("Retire da frigideira e corte ao meio");
        sanduiche.adicionarPasso("Sirva imediatamente");
        
        // Receita 4 - Salada (vegetariana)
        Receita salada = new Receita("Salada Completa", 15, true);
        salada.adicionarIngrediente(new Ingrediente("alface", 1));
        salada.adicionarIngrediente(new Ingrediente("tomate", 2));
        salada.adicionarIngrediente(new Ingrediente("cenoura", 1));
        salada.adicionarIngrediente(new Ingrediente("cebola", 1));
        salada.adicionarIngrediente(new Ingrediente("azeite", 2));
        salada.adicionarIngrediente(new Ingrediente("vinagre", 1));
        salada.adicionarPasso("Lave bem todos os vegetais sob água corrente");
        salada.adicionarPasso("Rasgue as folhas de alface com as mãos em pedaços médios");
        salada.adicionarPasso("Corte os tomates em fatias ou cubos, conforme preferência");
        salada.adicionarPasso("Rale a cenoura no ralo grosso");
        salada.adicionarPasso("Corte a cebola em fatias finas");
        salada.adicionarPasso("Em uma tigela grande, misture todos os vegetais");
        salada.adicionarPasso("Em uma tigela pequena, misture azeite, vinagre e sal");
        salada.adicionarPasso("Regue a salada com o molho e misture delicadamente");
        salada.adicionarPasso("Sirva imediatamente");
        
        // Receita 5 - Ovo mexido (vegetariana)
        Receita ovoMexido = new Receita("Ovo Mexido Cremoso", 10, true);
        ovoMexido.adicionarIngrediente(new Ingrediente("ovo", 3));
        ovoMexido.adicionarIngrediente(new Ingrediente("leite", 2));
        ovoMexido.adicionarIngrediente(new Ingrediente("sal", 1));
        ovoMexido.adicionarIngrediente(new Ingrediente("manteiga", 1));
        ovoMexido.adicionarPasso("Quebre os ovos em uma tigela");
        ovoMexido.adicionarPasso("Adicione o leite e o sal");
        ovoMexido.adicionarPasso("Bata os ovos com um garfo até ficarem bem misturados");
        ovoMexido.adicionarPasso("Derreta a manteiga em uma frigideira em fogo baixo-médio");
        ovoMexido.adicionarPasso("Despeje a mistura de ovos na frigideira");
        ovoMexido.adicionarPasso("Espere alguns segundos até começar a cozinhar nas bordas");
        ovoMexido.adicionarPasso("Com uma espátula, empurre os ovos das bordas para o centro");
        ovoMexido.adicionarPasso("Continue mexendo suavemente até os ovos estarem cremosos");
        ovoMexido.adicionarPasso("Retire do fogo antes de ficarem completamente sólidos");
        ovoMexido.adicionarPasso("Sirva imediatamente com pão torrado");
        
        // Adicionar todas ao banco
        banco.add(omelete);
        banco.add(panqueca);
        banco.add(sanduiche);
        banco.add(salada);
        banco.add(ovoMexido);
        
        return banco;
    }
    
    /**
     * Filtra receitas por compatibilidade mínima.
     * 
     * @param receitas Lista de receitas
     * @param usuario Usuário para cálculo
     * @param compatibilidadeMinima % mínima de compatibilidade (0-100)
     * @return Receitas filtradas
     */
    protected ArrayList<Receita> filtrarPorCompatibilidade(ArrayList<Receita> receitas, 
                                                          Usuario usuario, 
                                                          int compatibilidadeMinima) {
        ArrayList<Receita> filtradas = new ArrayList<>();
        
        for (Receita receita : receitas) {
            int compatibilidade = calcularCompatibilidade(usuario, receita);
            if (compatibilidade >= compatibilidadeMinima) {
                filtradas.add(receita);
            }
        }
        
        return filtradas;
    }
    
    /**
     * Aplica todos os filtros básicos do usuário.
     * 
     * @param receitas Lista de receitas
     * @param usuario Usuário com preferências
     * @param tempoMaximo Tempo máximo em minutos
     * @param compatibilidadeMinima % mínima de compatibilidade
     * @return Receitas filtradas
     */
    protected ArrayList<Receita> aplicarFiltrosBasicos(ArrayList<Receita> receitas,
                                                      Usuario usuario,
                                                      int tempoMaximo,
                                                      int compatibilidadeMinima) {
        // 1. Filtrar por tempo
        ArrayList<Receita> filtradas = filtrarPorTempo(receitas, tempoMaximo);
        
        // 2. Filtrar por vegetarianismo se necessário
        if (usuario.isVegetariano()) {
            filtradas = filtrarVegetarianas(filtradas);
        }
        
        // 3. Filtrar por compatibilidade mínima
        filtradas = filtrarPorCompatibilidade(filtradas, usuario, compatibilidadeMinima);
        
        return filtradas;
    }
    
    // ========== MÉTODOS DE UTILIDADE ==========
    
    /**
     * Exibe informações sobre o sugestor (para debug/demonstração).
     */
    protected void exibirInformacoesSugestor(String nomeSugestor, Usuario usuario) {
        System.out.println("\n🧠 " + nomeSugestor.toUpperCase());
        System.out.println("   Usuário: " + usuario.getNome());
        System.out.println("   Ingredientes disponíveis: " + usuario.getIngredientesDisponiveis().size());
        System.out.println("   Vegetariano: " + (usuario.isVegetariano() ? "Sim" : "Não"));
        System.out.println("   Buscando receitas...");
    }
    
    /**
     * Exibe resultados da sugestão.
     */
    protected void exibirResultadosSugestao(ArrayList<Receita> receitas) {
        if (receitas.isEmpty()) {
            System.out.println("   ❌ Nenhuma receita encontrada com os critérios atuais.");
            return;
        }
        
        System.out.println("   ✅ Encontradas " + receitas.size() + " receitas adequadas");
        
        for (int i = 0; i < receitas.size(); i++) {
            Receita r = receitas.get(i);
            System.out.println("   " + (i+1) + ". " + r.getNome() + 
                " (" + r.getTempoPreparo() + "min, " +
                (r.isVegetariana() ? "🥬" : "🍗") + ")");
        }
    }
    
    /**
     * Cria uma mensagem de resumo da sugestão.
     */
    protected String criarResumoSugestao(ArrayList<Receita> receitas, Usuario usuario) {
        if (receitas.isEmpty()) {
            return "Nenhuma receita encontrada para " + usuario.getNome();
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("🍳 Sugestões para ").append(usuario.getNome()).append(":\n");
        
        for (int i = 0; i < receitas.size(); i++) {
            Receita r = receitas.get(i);
            int compatibilidade = calcularCompatibilidade(usuario, r);
            
            sb.append(i + 1).append(". ").append(r.getNome())
              .append(" (").append(r.getTempoPreparo()).append("min, ")
              .append(compatibilidade).append("% compatível)")
              .append(r.isVegetariana() ? " 🥬" : " 🍗")
              .append("\n");
        }
        
        return sb.toString();
    }
}