package fr.iutfbleau.projetIHM2021FI2.Stats.View;
import fr.iutfbleau.projetIHM2021FI2.Stats.Controller.ListenOccupD;
import javax.swing.*;
import java.awt.*;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 *La classe <code>Window_stats</code> est utilisée pour afficher sous forme d'une fenêtre des informations à l'écran.
 */



public class Window_stats extends JFrame {
    /**
    *Le constructeur permet d'afficher la fenêtre ainsi que toutes ses composantes.
    */

    
    public Window_stats() {

		//fenetre
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        this.setTitle("Hôtel 5*");
        this.setSize((int) width, (int) height);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        JPanel panel2 = new JPanel();
        GridBagConstraints gbcpa = new GridBagConstraints();

        
		//Titre
        JLabel stats = new JLabel("Stats");
        gbcpa.gridx = 5;
        gbcpa.gridy = 2;
        gbcpa.gridwidth = 2;
        gbcpa.gridheight = 1;
        gbcpa.fill = GridBagConstraints.BOTH;
        gbcpa.anchor = GridBagConstraints.CENTER;
        gbcpa.weightx = 0.0;
        gbcpa.weighty = 0.0;

        Font font = new Font("Arial", Font.BOLD, 30);
        stats.setFont(font);
        stats.setPreferredSize(new Dimension(350, 200));
        panel.add(stats, gbcpa);


        //Boutons radios

        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButton jour = new JRadioButton("1 Jour");
        jour.setActionCommand("jour");
        JRadioButton semaine = new JRadioButton("1 Semaine");
        semaine.setActionCommand("semaine");
        JRadioButton mois1 = new JRadioButton("1 Mois");
        mois1.setActionCommand("mois1");
        JRadioButton mois3 = new JRadioButton("3 Mois");
        mois3.setActionCommand("mois3");
        buttonGroup.add(jour);
        buttonGroup.add(semaine);
        buttonGroup.add(mois1);
        buttonGroup.add(mois3);

        gbcpa.gridwidth = 1;
        gbcpa.gridx = 2;
        gbcpa.gridy = 3;

        panel.add(jour, gbcpa);
        gbcpa.gridx = 3;
        panel.add(semaine, gbcpa);
        gbcpa.gridx = 4;
        panel.add(mois1, gbcpa);
        gbcpa.gridx = 5;
        panel.add(mois3, gbcpa);
        jour.setSelected(true);

        //spinner

        SimpleDateFormat model = new SimpleDateFormat("dd/MM/yyyy");
        JSpinner spinner = new JSpinner(new SpinnerDateModel());
        spinner.setEditor(new JSpinner.DateEditor(spinner, model.toPattern()));
        Calendar calendar = new GregorianCalendar(2018, Calendar.JANUARY, 9);
        spinner.setValue(calendar.getTime());
        gbcpa.gridx = 2;
        gbcpa.gridy = 4;
        gbcpa.gridwidth = 2;
        gbcpa.gridheight = 1;
        gbcpa.fill = GridBagConstraints.BOTH;
        gbcpa.anchor = GridBagConstraints.CENTER;
        gbcpa.weightx = 0.0;
        gbcpa.weighty = 0.0;
        panel.add(spinner, gbcpa);

        //Bouton pour lancer l'affichage du % d'occupation de l'hotel

        JButton b1 = new JButton("Voir l'occupation");
        gbcpa.gridx = 5;
        gbcpa.gridy = 4;
        gbcpa.gridwidth = 1;
        gbcpa.gridheight = 1;
        gbcpa.fill = GridBagConstraints.BOTH;
        gbcpa.anchor = GridBagConstraints.CENTER;
        gbcpa.weightx = 0.0;
        gbcpa.weighty = 0.0;
        panel.add(b1, gbcpa);

	//message erreur
	
	
	 JLabel chargement = new JLabel("Le diagramme s'affiche après un certain temps, veuillez nous excuser.");
	 /*Font font2 = new Font("Arial", Font.BOLD, 12);
	 chargement.setFont(font2);
	 */
	   gbcpa.gridx = 5;
	 gbcpa.gridy = 1;
	
	 panel.add(chargement,gbcpa);
	
	 

	 
	 
        ListenOccupD listener1 = new ListenOccupD(spinner, buttonGroup, this, panel2);
        b1.addActionListener(listener1);
        this.add(panel, BorderLayout.NORTH);
        this.setVisible(true);
    }


}
