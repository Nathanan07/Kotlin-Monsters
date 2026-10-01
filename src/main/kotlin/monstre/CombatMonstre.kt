package monstre

import joueur
import monstre.EspeceMonstre
import monstre.IndividuMonstre
import kotlin.math.exp
import dressseur.Entraineur
import item.Utilisable

class CombatMonstre (var monstreJoueur : IndividuMonstre, var monstreSauvage : IndividuMonstre) {
    var round : Int = 1

    fun gameOver(): Boolean {
        var defaite : Boolean = false
        if (monstreJoueur.pv < 0) {
            defaite = true
            return defaite
        } else {
            return false
        }
    }

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

    fun  actionAdversaire() {
        if (monstreSauvage.pv > 0) {
            monstreSauvage.attaquer(monstreJoueur)
        }
    }

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

    fun afficheCombat() {
        println("======== Début Round : $round ========")
        println("Niveau : ${monstreSauvage.niveau}")
        println("PV : ${monstreSauvage.pv} / ${monstreSauvage.pvMax}")
        println(monstreSauvage.espece.afficheArt())
        println(monstreSauvage.espece.afficheArt(false))
        println("Niveau : ${monstreJoueur.niveau}")
        println("PV : ${monstreSauvage.pv} / ${monstreJoueur.pvMax}")
    }

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