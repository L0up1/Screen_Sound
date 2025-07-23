package com.example.desafioScreenSound.Principal;

import com.example.desafioScreenSound.Model.Artista;
import com.example.desafioScreenSound.Model.Musica;
import com.example.desafioScreenSound.Model.TipoArtista;
import com.example.desafioScreenSound.Repository.ArtistaRepository;
import com.example.desafioScreenSound.Services.ConsultaChatGPT;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private final ArtistaRepository repositorio;
    private Scanner leitura = new Scanner(System.in);

    public Principal(ArtistaRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 9) {
          String menu = """
                    1 - Cadastrar artistas
                    2 - Cadastras músicas
                    3 - Listar músicas
                    4 - Buscas músicas pelo artista
                    5 - Pesquisar dados sobre um artista
                    
                    9 - Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscasArtistas();
                    break;
                case 2:
                    buscasMusicas();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    buscasMusicaPeloArtista();
                    break;
                case 5:
                    buscarDadosArtistas();
                    break;
                case 9:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void buscarDadosArtistas() {
        System.out.println("Pesquisar dados sobre qual artista?");
        var nome = leitura.nextLine();
        var resposta = ConsultaChatGPT.obterDados(nome);
        System.out.println(resposta.trim());
    }

    private void buscasMusicaPeloArtista() {
        System.out.println("Buscar músicas de que artistas?");
        var nome = leitura.nextLine();
        List<Musica> musicas = repositorio.buscaMusicasPorArtista(nome);
        musicas.forEach(System.out::println);
    }

    private void listarMusicas() {
        List<Artista> artistas = repositorio.findAll();
        artistas.forEach(a -> a.getMusicas().forEach(System.out::println));
    }

    private void buscasMusicas() {
        System.out.println("Cadastrar música de que artista?");
        var nome = leitura.nextLine();
        Optional<Artista> artista = repositorio.findByNomeArtistaContainingIgnoreCase(nome);
        if (artista.isPresent()) {
            System.out.println("Informe o título da música: ");
            var nomeMusica = leitura.nextLine();
            Musica musica = new Musica(nomeMusica);
            musica.setArtista(artista.get());
            artista.get().getMusicas().add(musica);
            repositorio.save(artista.get());
        } else {
            System.out.println("Artista não encontrado");
        }
     }

    private void buscasArtistas() {
        var cadastrarNovo = "S";

        while (cadastrarNovo.equalsIgnoreCase("s")) {
            System.out.println("Qual o nome do artista você quer buscar:");
            String nome = leitura.nextLine();
            System.out.println("Qual o tipo de artistas: (solo, dupla ou banda");
            String tipo = leitura.nextLine();
            TipoArtista tipoArtista = TipoArtista.valueOf(tipo.toUpperCase());
            Artista artista = new Artista(nome, tipoArtista);
            repositorio.save(artista);
            System.out.println("Cadastrar novo artista?  (S/N)");
            cadastrarNovo = leitura.nextLine();
        }
    }
}
