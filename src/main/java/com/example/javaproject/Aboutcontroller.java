package com.example.javaproject;

        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.Alert;
        import javafx.scene.control.TextField;
        import javafx.scene.image.ImageView;
        import javafx.scene.input.MouseEvent;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.HBox;
        import javafx.scene.layout.VBox;
        import javafx.stage.Stage;

        import java.net.URL;
        import java.sql.*;
        import java.util.List;
        import java.util.ResourceBundle;

        import static com.sun.glass.ui.Cursor.setVisible;

public class Aboutcontroller implements Initializable {

    @FXML
    private ImageView addavionicon1;

    @FXML
    private ImageView addicon;

    @FXML
    private ImageView avataravionicon;

    @FXML
    private ImageView avataricon;

    @FXML
    private AnchorPane avion;

    @FXML
    private TextField capacite;

    @FXML
    private AnchorPane container;

    @FXML
    private ImageView deleteavionicon1;

    @FXML
    private ImageView deleteicon;

    @FXML
    private TextField id_av;

    @FXML
    private TextField id_pil;

    @FXML
    private ImageView modifyavionicon1;

    @FXML
    private ImageView modifyicon;

    @FXML
    private TextField nom_pil;

    @FXML
    private AnchorPane inerfpilot,interavio;

    @FXML
    private TextField pr_pil,pr_pil1,nom_pil1;

    @FXML
    private ImageView prec_av,okmodpil;

    @FXML
    private ImageView prec_pil;

    @FXML
    private ImageView suiv_av;

    @FXML
    private ImageView suiv_pil;

    @FXML
    private TextField type;
    private AboutDAO aboutDAO;

    private List<Pilote> pilotes;

    private int currentIndex ,currentIndexxx;
    @FXML
    private HBox ajpilote;

    @FXML
    private ImageView okpil;
    @FXML
    private TextField prlab;
    @FXML
    private TextField idlab;
    @FXML
    private TextField namlab,tyyyyp,caap;
    @FXML
    private VBox modifpilote,modifavion;
    @FXML
    private HBox ajavion;
    private List<Avion> avions;

    public  void initialize(URL location, ResourceBundle resources) {

        AboutDAO aboutDAO= new AboutDAO();
        ajpilote.setVisible(false);
        modifpilote.setVisible(false);
        ajavion.setVisible(false);
        modifavion.setVisible(false);

        try {
            pilotes = aboutDAO.getAllPilotes();
            avions=aboutDAO.getAllavions();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        verify_empty();
        verify_empty1();
        currentIndex = 0;
        if (!pilotes.isEmpty()){
            showPilote(currentIndex);
        }
        else {
            System.out.println("fergha l base a m3alem");
        }
        currentIndexxx = 0;
        if (!pilotes.isEmpty()){
            showplane(currentIndex);
        }
        else {
            System.out.println("fergha l base a m3alem");
        }

    }
    private void showPilote(int index) {
        System.out.println(pilotes.size());
       // System.out.println(++currentIndex);
        Pilote pilote = pilotes.get(index);
        nom_pil.setText(pilote.getNom());
        pr_pil.setText(pilote.getPrenom());
        id_pil.setText(Integer.toString(pilote.ID_pilote));
        // Afficher l'avatar
        //Image avatar = new Image(pilote.getAvatar());
      //  avatarImageView.setImage(avatar);
    }
    private void showplane(int index) {
        System.out.println(avions.size());
        Avion avion=avions.get(index);

        id_av.setText(Integer.toString(avion.getID_avion()));
        type.setText(avion.getType());
        capacite.setText(Integer.toString(avion.getCapacite()));
        // Afficher l'avatar
        //Image avatar = new Image(pilote.getAvatar());
        //  avatarImageView.setImage(avatar);
    }
    @FXML
    private void nextpilote(){
        if (pilotes.size()>0 && ++currentIndex < pilotes.size() ){
            showPilote(currentIndex);
        }
        else {
            currentIndex --;
        }
    }
    @FXML
    private void nextavion(){
        if (avions.size()>0 && ++currentIndexxx < avions.size() ){

            showplane(currentIndexxx);
        }
        else {
            currentIndexxx --;
        }
    }
    @FXML
    private void modif_pil(){
        nom_pil.setVisible(false);
        pr_pil.setVisible(false);
        modifpilote.setVisible(true);

        pr_pil1.setText(pr_pil.getText());
        nom_pil1.setText(nom_pil.getText());
    }

    @FXML
    private void modif_avi(){
        type.setVisible(false);
        capacite.setVisible(false);
        modifavion.setVisible(true);
        tyyyyp.setText(type.getText());
        caap.setText(capacite.getText());

    }

    @FXML
    private void modif_pilote() throws SQLException {
        aboutDAO=new AboutDAO();
        if(aboutDAO != null){
        String  nompill=pr_pil1.getText();
        String prpill=nom_pil1.getText();
        pilotes.get(currentIndex).setNom(nompill);
        pilotes.get(currentIndex).setPrenom(prpill);
        aboutDAO.update(pilotes.get(currentIndex));
        showPilote(currentIndex);
            try {
                pilotes = aboutDAO.getAllPilotes();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        nom_pil.setVisible(true);
        pr_pil.setVisible(true);
        modifpilote.setVisible(false);
        }
        else {
            System.out.println("oooooooooooooooooooh");
        }
    }

    @FXML
    private void modif_avion() throws SQLException {
        aboutDAO=new AboutDAO();
        if(aboutDAO != null){
            String  tyyy=tyyyyp.getText();
            String caaaaap=caap.getText();
            avions.get(currentIndexxx).setCapacite(Integer.parseInt(caaaaap));
            avions.get(currentIndexxx).setType(tyyy);
            aboutDAO.updateav(avions.get(currentIndexxx));
            showplane(currentIndexxx);

            try {
                avions=aboutDAO.getAllavions();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            type.setVisible(true);
            capacite.setVisible(true);
            modifavion.setVisible(false);
        }
        else {
            System.out.println("oooooooooooooooooooh");
        }
    }
    @FXML
    private void supp_pilote() throws SQLException {
        aboutDAO=new AboutDAO();
        aboutDAO.delete(pilotes.get(currentIndex));
        currentIndex=0;
        pilotes = aboutDAO.getAllPilotes();
        verify_empty();
        if (!pilotes.isEmpty()){
            showPilote(currentIndex);
        }
        else {
            System.out.println("fergha l base a m3alem");
        }
    }

    @FXML
    private void supp_avion() throws SQLException {
        aboutDAO=new AboutDAO();

        aboutDAO.deleteav(avions.get(currentIndexxx));
        currentIndexxx=0;
        avions=aboutDAO.getAllavions();
        verify_empty1();
        if (!pilotes.isEmpty()){
            showplane(currentIndexxx);
        }
        else {
            System.out.println("fergha l base a m3alem");
        }
    }
    private void verify_empty1(){
        if (avions.isEmpty()){
            //  inerfpilot.setDisable(true);
            interavio.setVisible(false);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(" Avion  !!!!!!! ");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez ajouter des nouveaux avions dans la base");
            alert.showAndWait();
            return;
        }
        else {
            interavio.setVisible(true);
            //inerfpilot.setDisable(false);
        }
    }
    private void verify_empty(){
        if (pilotes.isEmpty()){
           //  inerfpilot.setDisable(true);
            inerfpilot.setVisible(false);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(" pilote !!!!!!! ");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez ajouter des nouveaux pilotes dans la base");
            alert.showAndWait();
            return;
        }
        else {
            inerfpilot.setVisible(true);
            //inerfpilot.setDisable(false);
        }
    }
    @FXML
    private void lastpilote(){
        if ( pilotes.size()>0 && --currentIndex >= 0 ){
            showPilote(currentIndex);
        }
        else {
            currentIndex ++;
        }
    }
    @FXML
    private void lastavion(){
        if ( avions.size()>0 && --currentIndexxx >= 0 ){
            showplane(currentIndexxx);
        }
        else {
            currentIndexxx ++;
        }
    }
    @FXML
    public void ajout_pill(MouseEvent event){
        ajpilote.setVisible(true);
    }
    @FXML
    public void ajout_avio(MouseEvent event){
        ajavion.setVisible(true);
    }
    @FXML
    private  TextField idavvv,tuy,caaaaa;

    @FXML
    public void ajout_avion(MouseEvent event) throws SQLException {
        aboutDAO=new AboutDAO();
        String  ida=idavvv.getText();
                String typ=tuy.getText();
                String cappp=caaaaa.getText();
        // String nvol=numvol.getText();
        if(typ.isEmpty() || cappp.isEmpty() || ida.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs vides");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }
        Connection connection=Myconnection.connect();
        //INSERT INTO `passenger` (`ID_passenger`, `nom`, `prenom`, `num_passeport`) VALUES
        String query ="INSERT INTO `avion` (`ID_avion`, `type`, `capacite`) VALUES(?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(ida));
            statement.setString(2, typ);
            statement.setInt(3,Integer.parseInt(cappp) );
            statement.executeUpdate();
            ajavion.setVisible(false);
            try {
                avions=aboutDAO.getAllavions();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            currentIndexxx=0;
            showplane(currentIndexxx);
        }
    }

    @FXML
    public void ajout_pil(MouseEvent event) throws SQLException {

        String idpill=idlab.getText();
        String  nompill=namlab.getText();
        String prpill=prlab.getText();
       // String nvol=numvol.getText();
        if(idpill.isEmpty() || nompill.isEmpty() || prpill.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs vides");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }
        Connection connection=Myconnection.connect();
        //INSERT INTO `passenger` (`ID_passenger`, `nom`, `prenom`, `num_passeport`) VALUES
        String query ="INSERT INTO `pilote` (`ID_pilote`, `nom`, `prenom`) VALUES(?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(idpill));
            statement.setString(2, nompill);
            statement.setString(3, prpill);
            statement.executeUpdate();

            ajpilote.setVisible(false);
            try {
                pilotes = aboutDAO.getAllPilotes();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            currentIndex=0;
            showPilote(currentIndex);
        }
    }





}

