import java.util.ArrayList;

public class Receita {
    private String nome;
    private int tempoDePreparo;
    private ArrayList<Ingrediente> ingredientes;
    private ArrayList<String> passos;
    private boolean vegetariana; // NOVO: informação se a receita é vegetariana
    
    /**
     * Construtor COMPLETO com informação vegetariana
     */
    public Receita(String nome, int tempoDePreparo, boolean vegetariana) {
        this.nome = nome;
        this.tempoDePreparo = tempoDePreparo;
        this.vegetariana = vegetariana;
        this.ingredientes = new ArrayList<>();
        this.passos = new ArrayList<>();
    }
    
    /**
     * Construtor SIMPLES (mantém compatibilidade)
     * Por padrão, receita não é vegetariana
     */
    public Receita(String nome, int tempoDePreparo) {
        this(nome, tempoDePreparo, false);
    }
    
    /**
     * Construtor VAZIO
     */
    public Receita() {
        this("Sem Nome", 0, false);
    }
    
    // ========== MÉTODOS PARA ADICIONAR CONTEÚDO ==========
    
    public void adicionarIngrediente(Ingrediente ingrediente) {
        ingredientes.add(ingrediente);
    }
    
    public void adicionarPasso(String passo) {
        passos.add(passo);
    }
    
    // ========== GETTERS ==========
    
    public String getNome() {
        return nome;
    }
    
    public int getTempoPreparo() {
        return tempoDePreparo;
    }
    
    public ArrayList<Ingrediente> getIngredientes() {
        return new ArrayList<>(ingredientes); // Retorna cópia para segurança
    }
    
    public ArrayList<String> getPassos() {
        return new ArrayList<>(passos); // Retorna cópia para segurança
    }
    
    public boolean isVegetariana() {
        return vegetariana;
    }
    
    // ========== SETTERS ==========
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setTempoPreparo(int tempoDePreparo) {
        if (tempoDePreparo >= 0) {
            this.tempoDePreparo = tempoDePreparo;
        }
    }
    
    public void setVegetariana(boolean vegetariana) {
        this.vegetariana = vegetariana;
    }
    
    // ========== MÉTODOS DE UTILIDADE ==========
    
    /**
     * Retorna uma descrição resumida da receita
     */
    public String getDescricaoResumida() {
        return String.format("%s (%d min, %s)", 
            nome, 
            tempoDePreparo, 
            vegetariana ? "Vegetariana" : "Não vegetariana");
    }
    
    /**
     * Verifica se a receita contém um ingrediente específico
     */
    public boolean contemIngrediente(String nomeIngrediente) {
        for (Ingrediente ing : ingredientes) {
            if (ing.getNome().equalsIgnoreCase(nomeIngrediente)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Retorna os primeiros N passos (útil para preview)
     */
    public ArrayList<String> getPrimeirosPassos(int quantidade) {
        ArrayList<String> primeiros = new ArrayList<>();
        int limite = Math.min(quantidade, passos.size());
        
        for (int i = 0; i < limite; i++) {
            primeiros.add(passos.get(i));
        }
        
        return primeiros;
    }
    
    /**
     * Formata todos os passos em uma única string
     */
    public String getPassosFormatados() {
        if (passos.isEmpty()) {
            return "Nenhum passo disponível.";
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < passos.size(); i++) {
            sb.append((i + 1)).append(". ").append(passos.get(i));
            if (i < passos.size() - 1) {
                sb.append("\n");
            }
        }
        
        return sb.toString();
    }
    
    /**
     * Exibe a receita completa formatada
     */
    public void exibirReceitaCompleta() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("🍳 " + nome.toUpperCase());
        System.out.println("=".repeat(50));
        
        System.out.println("\n📊 INFORMAÇÕES:");
        System.out.println("• Tempo de preparo: " + tempoDePreparo + " minutos");
        System.out.println("• Tipo: " + (vegetariana ? "🥬 VEGETARIANA" : "🍗 NÃO VEGETARIANA"));
        
        System.out.println("\n🛒 INGREDIENTES (" + ingredientes.size() + "):");
        for (int i = 0; i < ingredientes.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + ingredientes.get(i));
        }
        
        System.out.println("\n📝 MODO DE PREPARO (" + passos.size() + " passos):");
        for (int i = 0; i < passos.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + passos.get(i));
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("🍽️  Bom apetite!");
        System.out.println("=".repeat(50));
    }
    
    // ========== MÉTODOS DE COMPATIBILIDADE ==========
    
    /**
     * Calcula quantos ingredientes desta receita o usuário tem
     */
    public int calcularIngredientesDisponiveis(Usuario usuario) {
        int contador = 0;
        
        for (Ingrediente ingrediente : ingredientes) {
            if (usuario.temIngrediente(ingrediente.getNome())) {
                contador++;
            }
        }
        
        return contador;
    }
    
    /**
     * Calcula porcentagem de ingredientes que o usuário tem
     */
    public int calcularPorcentagemCompatibilidade(Usuario usuario) {
        if (ingredientes.isEmpty()) {
            return 0;
        }
        
        int disponiveis = calcularIngredientesDisponiveis(usuario);
        return (disponiveis * 100) / ingredientes.size();
    }
    
    /**
     * Verifica se a receita é adequada para o usuário
     * Considera: ingredientes disponíveis, tempo e vegetarianismo
     */
    public boolean eAdequadaParaUsuario(Usuario usuario, int tempoMaximo) {
        // Verifica tempo
        if (tempoDePreparo > tempoMaximo) {
            return false;
        }
        
        // Verifica vegetarianismo
        if (usuario.isVegetariano() && !vegetariana) {
            return false;
        }
        
        // Verifica compatibilidade mínima (pelo menos 50%)
        int compatibilidade = calcularPorcentagemCompatibilidade(usuario);
        return compatibilidade >= 50;
    }
    
    // ========== toString e equals ==========
    
    @Override
    public String toString() {
        String vegIcon = vegetariana ? "🥬" : "🍗";
        return String.format("%s %s (%d min, %d ingredientes)", 
            vegIcon, nome, tempoDePreparo, ingredientes.size());
    }
    
    /**
     * Duas receitas são iguais se tiverem o mesmo nome
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Receita outra = (Receita) obj;
        return nome.equalsIgnoreCase(outra.nome);
    }
    
    @Override
    public int hashCode() {
        return nome.toLowerCase().hashCode();
    }
}