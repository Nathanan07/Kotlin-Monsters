package monstre

import joueur
import monstre.EspeceMonstre
import monstre.IndividuMonstre
import kotlin.math.exp
import dressseur.Entraineur
import item.Utilisable

/**
 * Représente un combat entre le monstre du joueur et un monstre sauvage.
 *
 * La classe permet de gérer les différentes actions du combat :
 * - déterminer quel monstre est le plus rapide ;
 * - faire attaquer le monstre du joueur ;
 * - faire attaquer le monstre adverse ;
 * - utiliser des objets ;
 * - changer de monstre ;
 * - vérifier si le combat est terminé.
 *
 * @property monstreJoueur Le monstre actuellement utilisé par le joueur.
 * @property monstreSauvage Le monstre sauvage affronté par le joueur.
 */

class CombatMonstre (var monstreJoueur : IndividuMonstre, var monstreSauvage : IndividuMonstre) {

    /**
     * Représente le numéro du round actuel du combat.
     */

    var round : Int = 1

    /**
     * Vérifie si le monstre du joueur est vaincu.
     *
     * Si les points de vie du monstre sont inférieurs à 0,
     * le combat est considéré comme terminé.
     *
     * @return true si le monstre du joueur est vaincu, sinon false.
     */

    fun gameOver(): Boolean {
        var defaite : Boolean = false
        if (monstreJoueur.pv < 0) {
            defaite = true
            return defaite
        } else {
            return false
        }
    }

    /**
     * Vérifie si le joueur gagne le combat.
     *
     * Le joueur gagne si le monstre sauvage n'a plus de points de vie.
     * Dans ce cas, le joueur reçoit de l'expérience.
     *
     * Le joueur peut également gagner si le monstre sauvage
     * appartient déjà à son entraîneur, ce qui correspond à une capture.
     *
     * @return true si le joueur gagne, sinon false.
     */

    fun joueurGagne() : Boolean{
        if (monstreSauvage.pv <= 0) {
            println("${joueur.nom} à gagné")
            var gainExp = monstreSauvage.exp * 0.20
            monstreSauvage.exp += gainExp
            println("${monstreSauvage.nom} gagne ${gainExp} exp.")
            return true
        } else {
            if (monstreSauvage.entraineur == joueur) {
                println("${monstreSauvage.nom} à été capturé")
                return true
            } else {
                return false
            }
        }
        return TODO("Provide the return value")
    }

    /**
     * Permet au monstre sauvage d'attaquer le monstre du joueur.
     *
     * L'attaque est effectuée uniquement si le monstre sauvage
     * possède encore des points de vie.
     */

    fun  actionAdversaire() {
        if (monstreSauvage.pv > 0) {
            monstreSauvage.attaquer(monstreJoueur)
        }
    }

    /**
     * Permet au joueur de choisir et d'effectuer une action pendant le combat.
     *
     * Les actions disponibles sont :
     * 1. Faire attaquer le monstre du joueur.
     * 2. Utiliser un objet du sac.
     * 3. Changer le monstre actuellement utilisé.
     *
     * @return true si le combat peut continuer, sinon false.
     */

    fun actionJoueur(): Boolean {
        if (gameOver() ==true) {
            return false
        } else {
            println("1 : le monstre du joueur attaque le monstre sauvage.\n" +
                    "2 : Utiliser un item\n" +
                    "3 : le joueur peut changer son monstre actuel contre un autre monstre de son équipe")
            var choixAction : String = readln()

            when (readlnOrNull()?.toIntOrNull()) {
                1 -> monstreJoueur.attaquer(monstreSauvage)
                2 -> {
                    joueur.sacAItems.forEachIndexed { index, item ->
                        println("${item.nom} ($index)")
                    }
                    var indexChoix = readln().toIntOrNull()
                    if (indexChoix != null) {
                        var objetChoisi = joueur.sacAItems.getOrElse(indexChoix, { null })
                        if (objetChoisi is Utilisable) {
                            var captureReussie = objetChoisi.utiliser(monstreSauvage)
                            if (captureReussie) {
                                return false
                            }
                        } else {
                            println("Objet non utilisable")
                        }
                    } else {
                        println("Objet pas trouvé")
                    }
                }
                3 -> {
                    if (joueur.equipeMonstre.size > 2) {
                        var dernierMonstre: Int = joueur.equipeMonstre.lastIndex
                        joueur.equipeMonstre.forEachIndexed { index, monstre ->
                            if (monstre.pv > 0) {
                                println("${monstre.nom} ($index)")
                                dernierMonstre = index
                            }
                        }

                        var indexChoix = readln().toIntOrNull() ?: 0
                        var choixMonstre =
                            joueur.equipeMonstre.getOrElse(indexChoix, { joueur.equipeMonstre.get(dernierMonstre) })
                        if (choixMonstre.pv <= 0) {
                            println("Impossible ! Ce monstre est Ko")
                        } else {
                            println("[${choixMonstre}] remplace [${monstreJoueur}]")
                            monstreJoueur = choixMonstre
                        }

                    } else {
                        println("Pas assez de monstres")
                        //return false
                    }
                }
                else -> {}
            }
            return true
        }
        return TODO("Provide the return value")
    }

    /**
     * Affiche les informations principales du combat.
     *
     * Les informations affichées comprennent :
     * - le numéro du round ;
     * - le niveau du monstre sauvage ;
     * - ses points de vie ;
     * - son apparence ;
     * - le niveau du monstre du joueur ;
     * - ses points de vie.
     */

    fun afficheCombat() {
        println("======== Début Round : $round ========")
        println("Niveau : ${monstreSauvage.niveau}")
        println("PV : ${monstreSauvage.pv} / ${monstreSauvage.pvMax}")
        println(monstreSauvage.espece.afficheArt())
        println(monstreSauvage.espece.afficheArt(false))
        println("Niveau : ${monstreJoueur.niveau}")
        println("PV : ${monstreSauvage.pv} / ${monstreJoueur.pvMax}")
    }

    /**
     * Lance le combat entre le monstre du joueur et le monstre sauvage.
     *
     * La vitesse des deux monstres est comparée afin de déterminer
     * lequel doit jouer en premier.
     *
     * Si le monstre du joueur est plus rapide, il effectue son action
     * en premier. Dans le cas contraire, le tour du monstre sauvage
     * est effectué en premier.
     */

    fun jouer() {
        val joueurPlusRapide = (monstreJoueur.vitesse >= monstreSauvage.vitesse)
        afficheCombat()
        var continuer : Boolean
        if (joueurPlusRapide) {
            continuer = actionJoueur()
            if (continuer == false) {
                return
            }
            actionJoueur()
        } else {
            actionJoueur()
            if (gameOver() == false) {
                continuer = actionJoueur()
                if (continuer == false) {
                    return
                }
            }
        }

    }

    /**
     * Lance le combat et gère les rounds jusqu'à la victoire ou la défaite.
     *
     * Affiche un message de fin si le joueur perd et restaure les PV
     * de tous ses monstres.
     */
    fun lanceCombat() {
        while (!gameOver() && !joueurGagne()) {
            this.jouer()
            println("======== Fin du Round : $round ========")
            round++
        }
        if (gameOver()) {
            joueur.equipeMonstre.forEach { it.pv = it.pvMax }
            println("Game Over !")
        }
    }

}