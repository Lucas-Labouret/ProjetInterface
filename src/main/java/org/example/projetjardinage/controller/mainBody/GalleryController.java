package org.example.projetjardinage.controller.mainBody;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.projetjardinage.GlobalData;
import org.example.projetjardinage.controller.Observer;
import org.example.projetjardinage.controller.utils.FrameGalleryController;
import org.example.projetjardinage.model.lists.ObservableList;
import org.example.projetjardinage.model.Species;
import org.example.projetjardinage.model.Specimen;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GalleryController extends Observer implements BodyController {


    @FXML
    private HBox photos;

    @FXML
    private HBox especes;
    int nbColumns = 4;

    private Species species = null;
    ArrayList<VBox> vBoxes = new ArrayList<>();





    private static class SpeciesViewPair {
        private final Species species;
        private final Parent view;

        public SpeciesViewPair(Species species, Parent view) {
            this.species = species;
            this.view = view;
        }



        public Species getSpecies() { return species; }
        public Parent getView() { return view; }
    }

    private final List<SpeciesViewPair> controllerViewPairs = new ArrayList<>();

    private final ObservableList<Species> plantes;

    private List<FrameGalleryController> frameGalleryControllers = new ArrayList<>();

    private Specimen specimen = null;

    public GalleryController() {
        plantes = GlobalData.species;
        this.subscribeTo(plantes);
    }

    public GalleryController(Species species) {
        this.species = species;
        plantes = GlobalData.species;
        this.subscribeTo(plantes);
    }


    public void initialize()  {
        update();
        //if(species == null){

        //}

        //Path dir = Paths.get("/galerie/Kougoro/Dame Grigri/");

        //try {
        //List<Path> imagePaths = Files.list(dir)
        //.filter(Files::isRegularFile)
        //.collect(Collectors.toList());


        //img1 = new ImageView(new Image(getClass().getResourceAsStream(imagePaths.get(0).toUri().toString())));
        //img2 = new ImageView(imagePaths.get(1).toUri().toString());

        //for (int i = 0; i < imagePaths.size(); i++) {
        //System.out.println(imagePaths.get(i).toUri());
        //Image image = new Image(imagePaths.get(i).toUri().toString());
        //ImageView imageView = new ImageView(image);
        //imageView.setFitHeight(100);
        //imageView.setFitWidth(100);
        //grid.add(imageView, i % 5, i / 5);
        //}

        //System.out.println(imagePaths);
        //} catch (IOException e) {
        //e.printStackTrace();
        //img1 = new ImageView();

        //Image image = new Image(getClass().getResourceAsStream("/icons/267203.png"));
        //System.out.println("tamsy");
        //img1.setImage( image );

        /**if(species != null){
            for (int i = 0; i < plantes.size(); i++) {
                if (plantes.get(i).equals(species)) {
                    loadImage(i, 0);
                    break;
                }
            }
        }**/
}
    private void loadImage(int i, int x){
        loadImage(plantes.get(i),x);
    }

    private void loadImage(int i){
        loadImage(plantes.get(i), -1);
    }

    private void loadImage(Species species, int x) {
        if (x == -1) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/FrameGallery.fxml"));
            FrameGalleryController frameGalleryController = new FrameGalleryController(species);
            loader.setController(frameGalleryController);

            Parent especeView;
            try {
                especeView = loader.load();
                controllerViewPairs.add(new SpeciesViewPair(species, especeView));
                frameGalleryControllers.add(frameGalleryController);
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {


            Path absolutePath = FileSystems.getDefault().getPath(
                    "src/main/resources/galerie/" + species.getOldName() +
                            "/" + species.getSpecimens().get(x).getOldName()
            ).toAbsolutePath();

            char[] a = absolutePath.toString().toCharArray();

            String t = "";

            for (char c : a) {
                t += c;
            }
            File dossier = new File(t);
            File[] listOfFiles = dossier.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/utils/FrameGallery.fxml"));
                FrameGalleryController frameGalleryController;
                try {
                    System.out.println("/galerie/" + species.getOldName() + "/" + species.getSpecimens().get(x).getOldName() + "/" + listOfFiles[i].getName());
                    frameGalleryController = new FrameGalleryController(species, x, "/galerie/" + species.getOldName() + "/" + species.getSpecimens().get(x).getOldName() + "/" + listOfFiles[i].getName());
                } catch (Exception e) {
                    frameGalleryController = new FrameGalleryController(species, x, "/icons/267203.png");
                    System.out.println("lololol");
                }
                loader.setController(frameGalleryController);

                Parent especeView;
                try {
                    especeView = loader.load();
                    photos.getChildren().add(especeView);
                    System.out.println("tamsy");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }



    }


    @Override
    public void update() {

        especes.getChildren().clear();
        vBoxes.clear();

        for (int i = 0; i < nbColumns; i++) {
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.TOP_CENTER);
            vBoxes.add(vBox);
            especes.getChildren().add(vBox);
        }
        ArrayList<Species> knownPlants = new ArrayList<>();
        for (SpeciesViewPair pair : controllerViewPairs) knownPlants.add(pair.getSpecies());
        photos.getChildren().clear();
        for (int i = 0; i < plantes.size(); i++) {
            if (!knownPlants.contains(plantes.get(i))) {
                loadImage(i);
            }
        }
        ArrayList<SpeciesViewPair> toRemove = new ArrayList<>();
        for (SpeciesViewPair pair : controllerViewPairs) {
            if (!plantes.contains(pair.getSpecies())) {
                toRemove.add(pair);
            }
        }
        for (SpeciesViewPair pair : toRemove) {
            controllerViewPairs.remove(pair);
        }

        int x = 0;
        for(SpeciesViewPair pair : controllerViewPairs) {
            vBoxes.get(x % nbColumns).getChildren().add(pair.getView());
            x++;
        }

        showSpecimens();

    }

    public void switchSpecies(Species species) {
        for (FrameGalleryController controller: frameGalleryControllers) {
            controller.resetBackground();
        }
        this.species = species;
        this.specimen = null;
        update();
    }

    public void switchSpecimen(Specimen specimen) {
        for (FrameGalleryController controller: frameGalleryControllers) {
            controller.resetBackground();
        }
        this.specimen = specimen;

        update();
    }

    public void showSpecimens() {
        if(species != null && specimen == null) {
            for (int i = 0; i < species.getSpecimens().size(); i++) {
                loadImage(species, i);
            }
        }else {
            if(specimen != null){
                loadImage(specimen.getSpecies(), specimen.getSpecies().getSpecimens().indexOf(specimen));
            }
        }
    }
    public void updateSize(double width, double height) {}
}
