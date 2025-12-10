import java.util.ArrayList;

/**
 * Sugestor que prioriza receitas RÁPIDAS (≤ 30 minutos).
 * Demonstra HERANÇA (estende SugestorBase) e POLIMORFISMO (implementa sugerirReceitas).
 */
public class SugestorRapido extends SugestorBase {
    
    /**
     * Implementação específica do SugestorRapido.
     * Foca em receitas com tempo mínimo de preparo.
     */
    @Override
public ArrayList<Receita> sugerirReceitas(Usuario usuario) {
    System.out.println("⚡ SUGESTOR RÁPIDO: Buscando receitas em até 30 minutos...");
    
    // 1. Obter todas as receitas disponíveis
    ArrayList<Receita> todasReceitas = criarBancoReceitasBasico();
    
    // 2. Aplicar filtro principal: tempo máximo 30min
    ArrayList<Receita> receitasRapidas = filtrarPorTempo(todasReceitas, 30);
    
    // 3. APLICAR FILTRO VEGETARIANO SE NECESSÁRIO
    if (usuario.isVegetariano()) {
        ArrayList<Receita> receitasVegetarianas = new ArrayList<>();
        for (Receita receita : receitasRapidas) {
            if (receita.isVegetariana()) {
                receitasVegetarianas.add(receita);
            }
        }
        receitasRapidas = receitasVegetarianas;
        System.out.println("   🌱 Filtro vegetariano ativado");
    }
    
    // 4. Ordenar por tempo (mais rápidas primeiro)
    receitasRapidas.sort((r1, r2) -> Integer.compare(r1.getTempoPreparo(), r2.getTempoPreparo()));
    
    // 5. Selecionar até 3 receitas com melhor compatibilidade
    ArrayList<Receita> sugestoes = new ArrayList<>();
    
    for (Receita receita : receitasRapidas) {
        int compatibilidade = calcularCompatibilidade(usuario, receita);
        
        if (compatibilidade >= 50) { // Pelo menos 50% de compatibilidade
            sugestoes.add(receita);
            
            if (sugestoes.size() >= 3) {
                break; // Limitar a 3 sugestões
            }
        }
    }
    
    System.out.println("   ✅ Encontradas " + sugestoes.size() + " receitas rápidas");
    return sugestoes;
}
    
    /**
     * Método específico do SugestorRapido.
     * Encontra a receita MAIS RÁPIDA possível.
     */
    public Receita sugerirReceitaMaisRapida(Usuario usuario) {
        ArrayList<Receita> receitasRapidas = sugerirReceitas(usuario);
        
        if (!receitasRapidas.isEmpty()) {
            return receitasRapidas.get(0); // A primeira é a mais rápida
        }
        
        return null;
    }
}