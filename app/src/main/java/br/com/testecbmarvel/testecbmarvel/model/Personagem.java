package br.com.testecbmarvel.testecbmarvel.model;

public class Personagem {
    private String id;
    private String nome;
    private String url;

    public Personagem(String id, String nome, String url){
        this.id = id;
        this.nome = nome;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
