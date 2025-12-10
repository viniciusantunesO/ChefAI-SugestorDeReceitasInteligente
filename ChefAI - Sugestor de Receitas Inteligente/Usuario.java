        import java.util.ArrayList;
        import java.util.List;
        
        /**
         * Classe que representa um usuário do sistema ChefAI
         * Armazena as preferências e ingredientes disponíveis do usuário
         */
        public class Usuario {
            private String nome;
            private List<Ingrediente> ingredientesDisponiveis;
            private List<Ingrediente> ingredientesNaoGosta;
            private boolean vegetariano;
            private boolean semLactose;
            private boolean semGluten;
            
            /**
             * Construtor básico do usuário
             * @param nome Nome do usuário
             */
            public Usuario(String nome) {
                this.nome = nome;
                this.ingredientesDisponiveis = new ArrayList<>();
                this.ingredientesNaoGosta = new ArrayList<>();
                this.vegetariano = false;
                this.semLactose = false;
                this.semGluten = false;
            }
            
            // ========== MÉTODOS PARA INGREDIENTES DISPONÍVEIS ==========
            
            /**
             * Adiciona um ingrediente à lista de disponíveis
             * @param ingrediente Ingrediente a ser adicionado
             */
            public void adicionarIngrediente(Ingrediente ingrediente) {
                ingredientesDisponiveis.add(ingrediente);
            }
            
            /**
             * Remove um ingrediente pelo nome
             * @param nomeIngrediente Nome do ingrediente a remover
             * @return true se removeu, false se não encontrou
             */
            public boolean removerIngrediente(String nomeIngrediente) {
                for (int i = 0; i < ingredientesDisponiveis.size(); i++) {
                    if (ingredientesDisponiveis.get(i).getNome().equalsIgnoreCase(nomeIngrediente)) {
                        ingredientesDisponiveis.remove(i);
                        return true;
                    }
                }
                return false;
            }
            
            /**
             * Verifica se tem um ingrediente específico
             * @param nomeIngrediente Nome do ingrediente
             * @return true se tem o ingrediente
             */
            public boolean temIngrediente(String nomeIngrediente) {
                for (Ingrediente ingrediente : ingredientesDisponiveis) {
                    if (ingrediente.getNome().equalsIgnoreCase(nomeIngrediente)) {
                        return true;
                    }
                }
                return false;
            }
            
            /**
             * Retorna um ingrediente específico pelo nome
             * @param nomeIngrediente Nome do ingrediente
             * @return O ingrediente ou null se não encontrar
             */
            public Ingrediente getIngrediente(String nomeIngrediente) {
                for (Ingrediente ingrediente : ingredientesDisponiveis) {
                    if (ingrediente.getNome().equalsIgnoreCase(nomeIngrediente)) {
                        return ingrediente;
                    }
                }
                return null;
            }
            
            // ========== MÉTODOS PARA INGREDIENTES QUE NÃO GOSTA ==========
            
            /**
             * Adiciona um ingrediente à lista de não gosta
             * @param ingrediente Ingrediente que não gosta
             */
            public void adicionarIngredienteNaoGosta(Ingrediente ingrediente) {
                ingredientesNaoGosta.add(ingrediente);
            }
            
            /**
             * Verifica se o usuário não gosta de um ingrediente
             * @param nomeIngrediente Nome do ingrediente
             * @return true se não gosta
             */
            public boolean naoGostaDe(String nomeIngrediente) {
                for (Ingrediente ingrediente : ingredientesNaoGosta) {
                    if (ingrediente.getNome().equalsIgnoreCase(nomeIngrediente)) {
                        return true;
                    }
                }
                return false;
            }
            
            // ========== GETTERS E SETTERS ==========
            
            public String getNome() {
                return nome;
            }
            
            public void setNome(String nome) {
                this.nome = nome;
            }
            
            public List<Ingrediente> getIngredientesDisponiveis() {
                return new ArrayList<>(ingredientesDisponiveis); // Retorna cópia para segurança
            }
            
            public List<Ingrediente> getIngredientesNaoGosta() {
                return new ArrayList<>(ingredientesNaoGosta);
            }
            
            public boolean isVegetariano() {
                return vegetariano;
            }
            
            public void setVegetariano(boolean vegetariano) {
                this.vegetariano = vegetariano;
            }
            
            public boolean isSemLactose() {
                return semLactose;
            }
            
            public void setSemLactose(boolean semLactose) {
                this.semLactose = semLactose;
            }
            
            public boolean isSemGluten() {
                return semGluten;
            }
            
            public void setSemGluten(boolean semGluten) {
                this.semGluten = semGluten;
            }
            
            // ========== MÉTODOS ÚTEIS ==========
            
            /**
             * Mostra todos os ingredientes disponíveis formatados
             * @return String formatada com os ingredientes
             */
            public String listarIngredientesDisponiveis() {
                if (ingredientesDisponiveis.isEmpty()) {
                    return "Nenhum ingrediente disponível";
                }
                
                StringBuilder sb = new StringBuilder();
                sb.append("Ingredientes disponíveis:\n");
                for (int i = 0; i < ingredientesDisponiveis.size(); i++) {
                    Ingrediente ing = ingredientesDisponiveis.get(i);
                    sb.append(String.format("%d. %s\n", i + 1, ing));
                }
                return sb.toString();
            }
            
            @Override
            public String toString() {
                return String.format("Usuário: %s | Ingredientes: %d | Vegetariano: %s",
                    nome, ingredientesDisponiveis.size(), vegetariano ? "Sim" : "Não");
            }
        }