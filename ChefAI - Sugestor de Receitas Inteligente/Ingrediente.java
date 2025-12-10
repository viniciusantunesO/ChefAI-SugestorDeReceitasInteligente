
/**
 * Escreva uma descrição da classe Ingredientes aqui.
 * 
 * @author (seu nome) 
 * @version (um número da versão ou uma data)
 */
public class Ingrediente
{
    private String nome;
    private int quantidade;
    
    public Ingrediente(String nome, int quantidade){
        this.nome=nome;
        this.quantidade=quantidade;
    }
    
    public boolean Tem(){
        int quant=getQuantidade();
        if(quant>0){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean usar(double quantidadeUsada) {
        if (quantidadeUsada <= quantidade) {
            quantidade -= quantidadeUsada;
            return true;
        }
        return false; // Não tem quantidade suficiente
    }
    
    public String getNome(){
        return nome;
    }
    
    public int getQuantidade(){
        return quantidade;
    }
    
    public void setQuantidade(int quantidade) {
        if (quantidade >= 0) {
            this.quantidade = quantidade;
        }
    }
    
    public String toString() {
    // Se quantidade for int, use %d
    return String.format("%d de %s", quantidade, nome);
}
}