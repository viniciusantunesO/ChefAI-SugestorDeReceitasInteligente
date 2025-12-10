import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class ConfiguracaoAPI {
    private static Properties props;
    
    public ConfiguracaoAPI() {
        // Construtor vazio
    }
    
    private static void garantirCarregado() {
        if (props != null) return;
        
        props = new Properties();
        System.out.println("=== INICIANDO CARREGAMENTO DA CONFIGURAÇÃO ===");
        
        try {
            // Método 1: Procurar arquivo manualmente
            File arquivo = encontrarArquivoConfig();
            
            if (arquivo != null && arquivo.exists()) {
                System.out.println("✅ Arquivo encontrado: " + arquivo.getAbsolutePath());
                System.out.println("📏 Tamanho: " + arquivo.length() + " bytes");
                
                FileReader reader = new FileReader(arquivo);
                props.load(reader);
                reader.close();
                
                System.out.println("✅ Configurações carregadas!");
                System.out.println("Chave API encontrada: " + 
                    (getApiKey().isEmpty() ? "NÃO" : "SIM (" + getApiKey().substring(0, Math.min(10, getApiKey().length())) + "...)"));
            } else {
                System.out.println("⚠️ Arquivo config.properties NÃO encontrado.");
                System.out.println("Usando modo de teste...");
                definirValoresPadrao();
            }
            
        } catch (Exception e) {
            System.out.println("❌ ERRO ao carregar: " + e.getMessage());
            definirValoresPadrao();
        }
        
        System.out.println("=== FIM DO CARREGAMENTO ===\n");
    }
    
    private static File encontrarArquivoConfig() {
        // Lista de lugares onde procurar
        String[] lugares = {
            "config.properties",                          // Pasta atual
            "./config.properties",                        // Pasta atual (outra notação)
            System.getProperty("user.dir") + "/config.properties",  // Pasta do projeto
            System.getProperty("user.dir") + "\\config.properties", // Windows
            "..\\config.properties",                      // Pasta acima (Windows)
            "../config.properties"                        // Pasta acima (Linux/Mac)
        };
        
        for (String lugar : lugares) {
            File arquivo = new File(lugar);
            System.out.println("Procurando em: " + arquivo.getAbsolutePath() + 
                             " - Existe? " + arquivo.exists());
            
            if (arquivo.exists() && arquivo.isFile() && arquivo.length() > 0) {
                return arquivo;
            }
        }
        
        return null;
    }
    
    private static void definirValoresPadrao() {
        props.setProperty("gemini.api.key", "");
        props.setProperty("gemini.api.url", 
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent");
    }
    
    public static String getApiKey() {
        garantirCarregado();
        String chave = props.getProperty("gemini.api.key", "");
        
        // Limpar e verificar
        chave = chave.trim();
        
        if (chave.isEmpty() || chave.equals("SUA_CHAVE_AQUI") || 
            chave.equals("COLE_SUA_CHAVE_AQUI") || chave.length() < 20) {
            return "";
        }
        
        return chave;
    }
    
    public static String getApiUrl() {
        garantirCarregado();
        return props.getProperty("gemini.api.url", 
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent");
    }
    
    // Método para testar manualmente
    public static void testeManual() {
        System.out.println("=== TESTE MANUAL DA CONFIGURAÇÃO ===");
        System.out.println("Chave API: " + getApiKey());
        System.out.println("URL API: " + getApiUrl());
        System.out.println("Chave válida? " + (!getApiKey().isEmpty() ? "✅ SIM" : "❌ NÃO"));
    }
}