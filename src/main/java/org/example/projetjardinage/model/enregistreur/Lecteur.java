package org.example.projetjardinage.model.enregistreur;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Lecteur {
    //prend le CSC de stockage et le lit
    private List <String> text;
    private final String path;

    private int bookmark;

    public Lecteur(String p)  {
        this.path = p;
        this.text = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String ligne = reader.readLine();
            while( ligne != null){
                String[] placeholder = ligne.split(",");
                ligne = reader.readLine();
                for(int i = 0; i < placeholder.length;i++){
                    this.text.add(placeholder[i]);
                }
            }
            reader.close();

            this.clean();




        } catch (FileNotFoundException e) {
            System.out.println("Problème à la lecture du fichier.");
            throw new RuntimeException(e);

        } catch (IOException e) {
            System.out.println("Problème de lecture de ligne à la lecture du fichier.");
            throw new RuntimeException(e);
        }



    }

    public List<List<String>> getMesures(){
        List<List<String>> mes = new ArrayList<>();
        if  (!((this.text.get(0)).equals("MES") ) ){
            System.out.println("ERREUR");
        }
        int nb = Integer.parseInt(this.text.get(1));
        for(int i = 0; i < nb; i=i+4){
            List<String> cas = new ArrayList<> (4);
            //4 cases par mesures, NOM, DESC, UNITE, TYPE
            cas.add(this.text.get(2+i));
            cas.add(this.text.get(3+i));
            cas.add(this.text.get(4+i));
            cas.add(this.text.get(5+i));

            mes.add(cas);
        }
        this.bookmark = (nb*4)+2;
        return mes;
    }

    public List<List<String>> getEsp(){
        List<List<String>> esp = new ArrayList<>();

        if  (!((this.text.get(this.bookmark)).equals("ESP") ) ){
            System.out.println("ERREUR_ESP");
        }
        int nb = Integer.parseInt(this.text.get(this.bookmark + 1));

        this.bookmark = this.bookmark+2;
        for(int i = 0; i < nb; i=i+1){
            List<String> cas = new ArrayList<> (9);
            //9 cases par mesures, NOM, FAV, PIC, NOTES, 5 * MESURES
            for(int j = 0; j < 9; j++){
                cas.add(this.text.get(this.bookmark));
                this.bookmark = this.bookmark+1;
            }
            esp.add(cas);
        }

        return esp;
    }



    public List<List<String>> getSpe(){
        List<List<String>> spe = new ArrayList<>();
        int cmpt = 0;
        if  (!((this.text.get(this.bookmark)).equals("SPE") ) ){
            System.out.println("ERREUR_SPE");
        }
        int nb = Integer.parseInt(this.text.get(this.bookmark + 1));
        this.bookmark = this.bookmark + 2;
        for(int i = 0; i < nb; i=i+1){
            List<String> cas = new ArrayList<> (7);
            //9 cases par mesures, NOM, DATE, ALIVE, PIC, SPE, NOTES1, NOTES2, JOURNAL
            for(int j = 0; j < 7; j++){
                cas.add(this.text.get(this.bookmark ));
                this.bookmark = this.bookmark+1;
            }
            int bmTMP = this.bookmark;  //marque-pages
            int nb2 = Integer.parseInt(this.text.get(bmTMP));
            if(nb2 == 0){    //si le journal est vide
                cas.add("0");
                this.bookmark = this.bookmark+1;
            } else {         //s'il y a des entrees, on recupere où c'est et c'est tout
                cas.add(String.valueOf(bmTMP)); //bookmark de l'indice du début des entrées de journal
                cmpt =0;
                int cmpTMP = 0;
                for (int k = 0; k<nb2 ; k++){ //pour le bookmark
                    cmpTMP = Integer.parseInt(this.text.get(bmTMP) +1 + cmpTMP) ;
                    cmpTMP++;
                    cmpt = cmpt + cmpTMP ;

                }
                this.bookmark = this.bookmark + cmpt;
            }

            spe.add(cas);

        }

        return spe;
    }

    public List<List<String>> getTasks(){
        List<List<String>> task = new ArrayList<>();

        if  (!((this.text.get(this.bookmark)).equals("TASK") ) ){
            System.out.println("ERREUR_TASK");
        }
        int nb = Integer.parseInt(this.text.get(this.bookmark + 1));
        for(int i = 0; i < nb; i=i+5){
            List<String> cas = new ArrayList<> (5);
            //4 cases par mesures, NOM, DONE, DEsCR, DDATE, SURTASK, REP, SPE, ESP
            for(int j = 2; j <8; j++){ //décalage des 2 premiers blocs
                cas.add(this.text.get(this.bookmark + j + i));
            }
            int nb2 = Integer.parseInt(this.text.get(this.bookmark + 8));  //nombre d'espece
            for(int j = 1; j <= nb2; j++){ //décalage des 2 premiers blocs
                cas.add(this.text.get(this.bookmark + 8 + j));
            }
            nb2 = Integer.parseInt(this.text.get(this.bookmark + 1 + nb2));  //nombre de specimen
            for(int j = 1; j <= nb2; j++){ //décalage des 2 premiers blocs
                cas.add(this.text.get(this.bookmark + 8 + j));
            }

            task.add(cas);
        }

        return task;
    }
    private void clean (){
        //remet les COM en virgule
        for(int i = 0; i<this.text.size();i++){
            String current = this.text.get(i);
            for(int j = 0; j<current.length()-2; j++){

                if((current.substring(j,j+3)).equals("<C>")){
                    String tmp1;
                    if (j==0){
                        tmp1 = "";
                    } else {
                        tmp1 = current.substring(0, j);
                    }
                    String tmp2 = current.substring(j+3, current.length());

                    this.text.set(i, tmp1 + "," + tmp2);
                    current = this.text.get(i);
                }
            }
        }
    }



}
