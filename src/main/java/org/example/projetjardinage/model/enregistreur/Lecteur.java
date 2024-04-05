package org.example.projetjardinage.model.enregistreur;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lecteur {
    //prend le CSC de stockage et le lit
    private List <String> text;
    private String path;

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

            for(int i = 0; i < this.text.size(); i++) {
                System.out.println(this.text.get(i));
            }


        } catch (FileNotFoundException e) {
            System.out.println("Problème à la lecture du fichier.");
            throw new RuntimeException(e);

        } catch (IOException e) {
            System.out.println("Problème de lecture de ligne à la lecture du fichier.");
            throw new RuntimeException(e);
        }



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
