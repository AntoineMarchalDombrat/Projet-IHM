package fr.iutfbleau.projetIHM2021FI2.Stats.Controller;
import fr.iutfbleau.projetIHM2021FI2.Stats.View.Diagramme;
import fr.iutfbleau.projetIHM2021FI2.Stats.Model.Jour;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;

import java.util.Arrays;
import java.util.Calendar;
import java.text.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *La classe <code>ListenOccupD</code>permet de réagir lors de la sélection d'un bouton.
 */
public class ListenOccupD implements ActionListener {

	private JSpinner spinner;
	private LocalDate date;
	private JPanel panel;
	private ButtonGroup buttonGroup;
	public JFrame fenetre;
    /**
     *Le constructeur permet de créer un listener adapté aux choix de l'utilisateur.
     */
	public ListenOccupD(JSpinner spinner, ButtonGroup buttonGroup, JFrame fenetre, JPanel panel) {
		this.spinner = spinner;
		this.buttonGroup = buttonGroup;
		this.fenetre = fenetre;
		this.panel = new JPanel();
		this.panel.setLayout(new BorderLayout());
		this.fenetre.add(this.panel);

	}

	public void actionPerformed(ActionEvent e) {

		String choice;
		int moyenne;
		choice = buttonGroup.getSelection().getActionCommand();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		this.date = LocalDate.parse(new SimpleDateFormat("yyyy/MM/dd").format(this.spinner.getValue()), formatter);
		Jour jour = new Jour(this.date);
		if (choice == "jour") {
			int[] data = new int[1];
			data = jour.getData(1);
			this.panel.removeAll();
			moyenne = jour.getMoyenne3ans();
			this.panel.removeAll();
			this.panel.add(new Diagramme(data, moyenne));

			this.fenetre.revalidate();

		} else if (choice == "semaine") {

			this.panel.removeAll();

			int[] data = new int[7];

			data = jour.getData(7);
			this.panel.add(new Diagramme(data, jour.getMoyenne3ans()));

			this.fenetre.revalidate();

		} else if (choice == "mois1") {
			this.panel.removeAll();
			int[] data = new int[31];

			data = jour.getData(31);

			this.panel.add(new Diagramme(data, jour.getMoyenne3ans()));
			this.fenetre.revalidate();
		} else {

			this.panel.removeAll();
			int[] data = new int[92];

			data = jour.getData(92);
			this.panel.add(new Diagramme(data, jour.getMoyenne3ans()));
			this.fenetre.revalidate();

		}

	}
}
