package Modelo;

public class TypeChart {
    public static double multiplicador(String tipoAtaque, String tipoAlvo) {
        tipoAtaque = tipoAtaque.toLowerCase();
        tipoAlvo = tipoAlvo.toLowerCase();

        switch (tipoAtaque) {
            case "eletrico":
                if (tipoAlvo.equals("agua") || tipoAlvo.equals("voador")) return 2.0;
                if (tipoAlvo.equals("planta") || tipoAlvo.equals("eletrico") || tipoAlvo.equals("dragao")) return 0.5;
                if (tipoAlvo.equals("terra")) return 0.0;
                break;
            case "fogo":
                if (tipoAlvo.equals("planta") || tipoAlvo.equals("gelo") || tipoAlvo.equals("inseto")) return 2.0;
                if (tipoAlvo.equals("agua") || tipoAlvo.equals("pedra")) return 0.5;
                break;
            case "agua":
                if (tipoAlvo.equals("fogo") || tipoAlvo.equals("pedra") || tipoAlvo.equals("terra")) return 2.0;
                if (tipoAlvo.equals("planta") || tipoAlvo.equals("eletrico")) return 0.5;
                break;
            case "planta":
                if (tipoAlvo.equals("agua") || tipoAlvo.equals("terra") || tipoAlvo.equals("pedra")) return 2.0;
                if (tipoAlvo.equals("fogo") || tipoAlvo.equals("voador")) return 0.5;
                break;
            case "psiquico":
                if (tipoAlvo.equals("lutador") || tipoAlvo.equals("veneno")) return 2.0;
                if (tipoAlvo.equals("psiquico")) return 0.5;
                break;
            case "fantasma":
                if (tipoAlvo.equals("psiquico")) return 2.0;
                if (tipoAlvo.equals("normal")) return 0.0;
                break;
            case "normal":
                if (tipoAlvo.equals("fantasma")) return 0.0;
                break;
            case "lutador":
                if (tipoAlvo.equals("normal") || tipoAlvo.equals("metal") || tipoAlvo.equals("pedra") || tipoAlvo.equals("noturno")) return 2.0;
                if (tipoAlvo.equals("psiquico") || tipoAlvo.equals("inseto") || tipoAlvo.equals("veneno") || tipoAlvo.equals("fada")) return 0.5;
                if (tipoAlvo.equals("fantasma")) return 0.0;
                break;

            case "pedra":
                if (tipoAlvo.equals("fogo") || tipoAlvo.equals("voador") || tipoAlvo.equals("gelo") || tipoAlvo.equals("inseto")) return 2.0;
                if (tipoAlvo.equals("lutador") || tipoAlvo.equals("terra") || tipoAlvo.equals("metal")) return 0.5;
                break;
            case "terra":
                if (tipoAlvo.equals("fogo") || tipoAlvo.equals("metal") || tipoAlvo.equals("pedra") || tipoAlvo.equals("eletrico")) return 2.0;
                if (tipoAlvo.equals("inseto") || tipoAlvo.equals("planta")) return 0.5;
                if (tipoAlvo.equals("voador")) return 0.0;
                break;
            case "metal":
                if (tipoAlvo.equals("pedra") || tipoAlvo.equals("fada") || tipoAlvo.equals("gelo")) return 2.0;
                if (tipoAlvo.equals("fogo") || tipoAlvo.equals("agua") || tipoAlvo.equals("metal") || tipoAlvo.equals("eletrico")) return 0.5;
                break;
            case "voador":
                if (tipoAlvo.equals("lutador") || tipoAlvo.equals("inseto") || tipoAlvo.equals("planta")) return 2.0;
                if (tipoAlvo.equals("metal") || tipoAlvo.equals("pedra") || tipoAlvo.equals("eletrico")) return 0.5;
                break;
            case "gelo":
                if (tipoAlvo.equals("terra") || tipoAlvo.equals("voador") || tipoAlvo.equals("planta") || tipoAlvo.equals("dragao")) return 2.0;
                if (tipoAlvo.equals("gelo") || tipoAlvo.equals("fogo") || tipoAlvo.equals("metal") || tipoAlvo.equals("agua")) return 0.5;
                break;
            case "inseto":
                if (tipoAlvo.equals("planta") || tipoAlvo.equals("psiquico") || tipoAlvo.equals("noturno")) return 2.0;
                if (tipoAlvo.equals("lutador") || tipoAlvo.equals("veneno") || tipoAlvo.equals("metal") || tipoAlvo.equals("fogo") || tipoAlvo.equals("fada") || tipoAlvo.equals("voador") || tipoAlvo.equals("fantasma")) return 0.5;
                break;
            case "noturno":
                if (tipoAlvo.equals("psiquico") || tipoAlvo.equals("fantasma")) return 2.0;
                if (tipoAlvo.equals("noturno") || tipoAlvo.equals("lutador") || tipoAlvo.equals("fada")) return 0.5;
                break;
            case "dragao":
                if (tipoAlvo.equals("dragao")) return 2.0;
                if (tipoAlvo.equals("metal")) return 0.5;
                if (tipoAlvo.equals("fada")) return 0.0;
                break;
            case "fada":
                if (tipoAlvo.equals("lutador") || tipoAlvo.equals("noturno") || tipoAlvo.equals("dragao")) return 2.0;
                if (tipoAlvo.equals("metal") || tipoAlvo.equals("fogo") || tipoAlvo.equals("veneno")) return 0.5;
                break;
            case "veneno":
                if (tipoAlvo.equals("planta") || tipoAlvo.equals("fada")) return 2.0;
                if (tipoAlvo.equals("veneno") || tipoAlvo.equals("fantasma") || tipoAlvo.equals("terra") || tipoAlvo.equals("pedra")) return 0.5;
                if (tipoAlvo.equals("metal")) return 0.0; 
                break;
        }

        return 1.0; // neutro
    }
}
