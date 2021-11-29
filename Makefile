# COMMANDES #
JAVAC = javac
# note $$ to get a single shell $
JAVAC_OPTIONS = -d build -cp build:$$CLASSPATH -implicit:none -sourcepath src

JAVA = java
JAR = jar
EXEC_JAR = ${JAVA} -jar

# CHEMINS RELATIFS
SRC = src/fr/iutfbleau/projetIHM2021FI2
BUILD = build/fr/iutfbleau/projetIHM2021FI2
DOC = doc/fr/iutfbleau/projetIHM2021FI2

# CHOIX NOMS
JAR_PROJET = projet.jar
JAR_PROJET2 = projet2.jar

# BUTS FACTICES #
.PHONY : run clean doc

# BUT PAR DEFAUT #

run : ${JAR_PROJET}
	${EXEC_JAR} ${JAR_PROJET}

run2 : ${JAR_PROJET2}
	${EXEC_JAR} ${JAR_PROJET2}

# AUTRE BUTS
doc :
	javadoc -d doc src/fr/iutfbleau/projetIHM2021FI2/API/*.java src/fr/iutfbleau/projetIHM2021FI2/MNP/*.java src/fr/iutfbleau/projetIHM2021FI2/View/Site/*.java

clean :
	rm -rf ${BUILD}/* *.jar


# REGLES DE DEPENDANCE #

## API ##
${BUILD}/API/MonPrint.class : ${SRC}/API/MonPrint.java 
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/API/MonPrint.java

${BUILD}/API/TypeChambre.class : ${SRC}/API/TypeChambre.java 
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/API/TypeChambre.java

${BUILD}/API/Chambre.class : ${SRC}/API/Chambre.java \
	  		     ${BUILD}/API/TypeChambre.class\
			     ${BUILD}/API/MonPrint.class
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/API/Chambre.java

${BUILD}/API/Client.class : ${SRC}/API/Client.java \
                            ${BUILD}/API/MonPrint.class
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/API/Client.java

${BUILD}/API/Prereservation.class : ${SRC}/API/Prereservation.java \
	  		            ${BUILD}/API/TypeChambre.class \
	  		     	    ${BUILD}/API/Client.class 
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/API/Prereservation.java

${BUILD}/API/Reservation.class : ${SRC}/API/Reservation.java \
	  		         ${BUILD}/API/Prereservation.class 
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/API/Reservation.java

${BUILD}/API/PrereservationFactory.class : ${SRC}/API/PrereservationFactory.java \
	  		            ${BUILD}/API/Prereservation.class 
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/API/PrereservationFactory.java

${BUILD}/API/ReservationFactory.class : ${SRC}/API/ReservationFactory.java \
	  		            ${BUILD}/API/Prereservation.class 
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/API/ReservationFactory.java

## MNP ##

${BUILD}/MNP/ClientNP.class : ${SRC}/MNP/ClientNP.java \
                              ${BUILD}/API/Client.class 
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/MNP/ClientNP.java

${BUILD}/MNP/ChambreNP.class : ${SRC}/MNP/ChambreNP.java \
                              ${BUILD}/API/Chambre.class 
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/MNP/ChambreNP.java

${BUILD}/MNP/PrereservationNP.class : ${SRC}/MNP/PrereservationNP.java \
			${BUILD}/MNP/ClientNP.class \
                        ${BUILD}/API/Prereservation.class 
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/MNP/PrereservationNP.java

${BUILD}/MNP/ReservationNP.class : ${SRC}/MNP/ReservationNP.java \
                              ${BUILD}/API/Reservation.class 
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/MNP/ReservationNP.java

${BUILD}/MNP/PrereservationFactory.class : ${SRC}/API/PrereservationFactory.java 
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/MNP/PrereservationFactory.java

${BUILD}/MNP/PrereservationFactoryNP.class : ${SRC}/MNP/PrereservationFactoryNP.java \
                              ${BUILD}/API/PrereservationFactory.class 
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/MNP/PrereservationFactoryNP.java


${BUILD}/MNP/ReservationFactoryNP.class : ${SRC}/MNP/ReservationFactoryNP.java \
			      ${BUILD}/MNP/ChambreNP.class \
			      ${BUILD}/MNP/ReservationNP.class \
                              ${BUILD}/API/ReservationFactory.class 
	${JAVAC} ${JAVAC_OPTIONS} ${SRC}/MNP/ReservationFactoryNP.java

## PAGE1 IHM FLORIAN ##

${BUILD}/View/Site/Controller_Main.class : ${SRC}/View/Site/Controller_Main.java \
			 ${BUILD}/View/Site/View_Window_reserv.class \
			 ${BUILD}/MNP/PrereservationNP.class \
			 ${BUILD}/MNP/PrereservationFactoryNP.class \
			 ${BUILD}/MNP/ReservationFactoryNP.class 
	${JAVAC} -Xlint:deprecation ${JAVAC_OPTIONS} ${SRC}/View/Site/Controller_Main.java

${BUILD}/View/Site/View_Window_reserv.class : ${SRC}/View/Site/View_Window_reserv.java \
			${BUILD}/View/Site/Model_Affiche_recherche.class \
			${BUILD}/View/Site/Model_Verif_bd.class \
			${BUILD}/View/Site/Model_Recup_bd.class \
			${BUILD}/View/Site/Fond.class
	${JAVAC} -Xlint:deprecation ${JAVAC_OPTIONS} ${SRC}/View/Site/View_Window_reserv.java

${BUILD}/View/Site/Model_Affiche_recherche.class : ${SRC}/View/Site/Model_Affiche_recherche.java \
			${BUILD}/View/Site/View_Affiche_recherche.class
	${JAVAC} -Xlint:deprecation ${JAVAC_OPTIONS} ${SRC}/View/Site/Model_Affiche_recherche.java

${BUILD}/View/Site/View_Affiche_recherche.class : ${SRC}/View/Site/View_Affiche_recherche.java \
			${BUILD}/View/Site/Controller_Affiche_choix.class
	${JAVAC} -Xlint:deprecation ${JAVAC_OPTIONS} ${SRC}/View/Site/View_Affiche_recherche.java

${BUILD}/View/Site/Controller_Affiche_choix.class : ${SRC}/View/Site/Controller_Affiche_choix.java \
			${BUILD}/View/Site/Model_Window_valide.class
	${JAVAC} -Xlint:deprecation ${JAVAC_OPTIONS} ${SRC}/View/Site/Controller_Affiche_choix.java

${BUILD}/View/Site/Model_Window_valide.class : ${SRC}/View/Site/Model_Window_valide.java \
			${BUILD}/View/Site/View_Window_valide.class
	${JAVAC} -Xlint:deprecation ${JAVAC_OPTIONS} ${SRC}/View/Site/Model_Window_valide.java

${BUILD}/View/Site/View_Window_valide.class : ${SRC}/View/Site/View_Window_valide.java \
			${BUILD}/View/Site/Controller_Affiche_home.class \
			${BUILD}/View/Site/Model_Valide_chambre.class \
			${BUILD}/View/Site/Model_Choisir_chambre.class
	${JAVAC} -Xlint:deprecation ${JAVAC_OPTIONS} ${SRC}/View/Site/View_Window_valide.java

${BUILD}/View/Site/Controller_Affiche_home.class : ${SRC}/View/Site/Controller_Affiche_home.java \
			${SRC}/View/Site/View_Window_reserv.java
	${JAVAC} -Xlint:deprecation ${JAVAC_OPTIONS} ${SRC}/View/Site/Controller_Affiche_home.java

${BUILD}/View/Site/Model_Verif_bd.class : ${SRC}/View/Site/Model_Verif_bd.java 
	${JAVAC} -Xlint:deprecation ${JAVAC_OPTIONS} ${SRC}/View/Site/Model_Verif_bd.java

${BUILD}/View/Site/Model_Recup_bd.class : ${SRC}/View/Site/Model_Recup_bd.java \
			${BUILD}/MNP/ClientNP.class
	${JAVAC} -Xlint:deprecation ${JAVAC_OPTIONS} ${SRC}/View/Site/Model_Recup_bd.java

${BUILD}/View/Site/Model_Valide_chambre.class : ${SRC}/View/Site/Model_Valide_chambre.java \
			${BUILD}/View/Site/View_Window_finish.class
	${JAVAC} -Xlint:deprecation ${JAVAC_OPTIONS} ${SRC}/View/Site/Model_Valide_chambre.java

${BUILD}/View/Site/View_Window_finish.class : ${SRC}/View/Site/View_Window_finish.java \
			${BUILD}/View/Site/Controller_Affiche_home.class
	${JAVAC} -Xlint:deprecation ${JAVAC_OPTIONS} ${SRC}/View/Site/View_Window_finish.java

${BUILD}/View/Site/Model_Choisir_chambre.class : ${SRC}/View/Site/Model_Choisir_chambre.java \
			${BUILD}/View/Site/View_Choisir_chambre.class
	${JAVAC} -Xlint:deprecation ${JAVAC_OPTIONS} ${SRC}/View/Site/Model_Choisir_chambre.java

${BUILD}/View/Site/View_Choisir_chambre.class : ${SRC}/View/Site/View_Choisir_chambre.java \
			${BUILD}/View/Site/Model_Affiche_retour.class
	${JAVAC} -Xlint:deprecation ${JAVAC_OPTIONS} ${SRC}/View/Site/View_Choisir_chambre.java

${BUILD}/View/Site/Model_Affiche_retour.class : ${SRC}/View/Site/Model_Affiche_retour.java \
			${SRC}/View/Site/Model_Window_valide.java
	${JAVAC} -Xlint:deprecation ${JAVAC_OPTIONS} ${SRC}/View/Site/Model_Affiche_retour.java

${BUILD}/View/Site/Fond.class : ${SRC}/View/Site/Fond.java 
	${JAVAC} -Xlint:deprecation ${JAVAC_OPTIONS} ${SRC}/View/Site/Fond.java


## PAGE2 IHM ANTOINE##

${BUILD}/Stats/Controller/Main.class : ${SRC}/Stats/Controller/Main.java \
			 ${BUILD}/Stats/View/Window_stats.class 
	${JAVAC} -Xlint:deprecation ${JAVAC_OPTIONS} ${SRC}/Stats/Controller/Main.java
	
${BUILD}/Stats/View/Window_stats.class : ${SRC}/Stats/View/Window_stats.java \
			 ${BUILD}/Stats/Controller/ListenOccupD.class \
			 ${BUILD}/Stats/View/Diagramme.class
	${JAVAC} -Xlint:deprecation ${JAVAC_OPTIONS} ${SRC}/Stats/View/Window_stats.java

${BUILD}/Stats/Controller/ListenOccupD.class : ${SRC}/Stats/Controller/ListenOccupD.java \
			${BUILD}/Stats/View/Diagramme.class \
			${BUILD}/Stats/Model/Jour.class
	${JAVAC} -Xlint:deprecation ${JAVAC_OPTIONS} ${SRC}/Stats/Controller/ListenOccupD.java

${BUILD}/Stats/View/Diagramme.class : ${SRC}/Stats/View/Diagramme.java
	${JAVAC} -Xlint:deprecation ${JAVAC_OPTIONS} ${SRC}/Stats/View/Diagramme.java

${BUILD}/Stats/Model/Jour.class : ${SRC}/Stats/Model/Jour.java
	${JAVAC} -Xlint:deprecation ${JAVAC_OPTIONS} ${SRC}/Stats/Model/Jour.java


# ## JARS ##

${JAR_PROJET} : ${BUILD}/View/Site/Controller_Main.class
	${JAR} cvfe ${JAR_PROJET} fr.iutfbleau.projetIHM2021FI2.View.Site.Controller_Main org/ res/ -C build fr

${JAR_PROJET2} : ${BUILD}/Stats/Controller/Main.class
	${JAR} cvfe ${JAR_PROJET2} fr.iutfbleau.projetIHM2021FI2.Stats.Controller.Main org/ -C build fr