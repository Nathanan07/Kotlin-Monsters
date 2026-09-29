package jeu

import dressseur.Entraineur
import joueur
import monstre.EspeceMonstre
import kotlin.math.pow
import kotlin.math.round
import kotlin.random.Random

/**
 * Représente un individu d'une espèce de monstre dans le contexte du jeu.
 *
 * Un individu possède ses propres caractéristiques, qui sont déterminées
 * à partir des statistiques de base de son espèce et peuvent évoluer
 * lorsqu'il gagne des niveaux.
 *
 * Chaque individu peut posséder un nom, un niveau, des points de vie,
 * de l'expérience, des statistiques de combat ainsi qu'un potentiel
 * d'évolution.
 *
 * Un individu peut également être associé à un entraîneur et peut
 * effectuer différentes actions telles que combattre, monter de niveau,
 * être renommé ou afficher ses caractéristiques.
 *
 * @property id L'identifiant unique de l'individu.
 * @property nom Le nom donné à l'individu.
 * @property espece L'espèce de monstre à laquelle appartient l'individu.
 *                  Cette propriété permet notamment d'accéder à ses
 *                  statistiques de base et à sa représentation artistique.
 * @property entraineur L'entraîneur auquel appartient l'individu.
 *                      Peut être null si le monstre n'est associé à aucun entraîneur.
 * @property niveau Le niveau actuel de l'individu.
 *                 Un individu commence au niveau 1 et peut augmenter de niveau
 *                 grâce à l'expérience acquise.
 * @property attaque La statistique d'attaque physique de l'individu.
 *                   Sa valeur initiale est déterminée à partir de l'attaque
 *                   de base de son espèce avec une variation aléatoire.
 * @property defense La statistique de défense physique de l'individu.
 *                   Sa valeur initiale est déterminée à partir de la défense
 *                   de base de son espèce avec une variation aléatoire.
 * @property vitesse La statistique de vitesse de l'individu.
 *                   Elle est initialement définie par la vitesse de base de son espèce.
 * @property attaqueSpe La statistique d'attaque spéciale de l'individu.
 *                     Elle est initialement définie par l'attaque spéciale
 *                     de base de son espèce.
 * @property defenseSpe La statistique de défense spéciale de l'individu.
 *                     Elle est initialement définie par la défense spéciale
 *                     de base de son espèce.
 * @property pvMax Le nombre maximal de points de vie de l'individu.
 *                 Sa valeur initiale est déterminée à partir des PV de base
 *                 de son espèce avec une variation aléatoire.
 * @property potentiel Le potentiel d'évolution de l'individu.
 *                     Cette valeur aléatoire influence l'augmentation de ses
 *                     statistiques lors de ses montées de niveau.
 * @property exp Le nombre actuel de points d'expérience de l'individu.
 *              La modification de cette propriété peut entraîner une ou
 *              plusieurs montées de niveau.
 * @property pv Le nombre actuel de points de vie de l'individu.
 *             Cette valeur est automatiquement limitée entre 0 et pvMax.
 *
 * @constructor Crée un nouvel individu de monstre à partir de son identifiant,
 *              de son nom, de son espèce, de son entraîneur éventuel et de
 *              son expérience initiale.
 */

class IndividuMonstres (
    var id : Int,
    var nom : String,
    var espece : EspeceMonstre,
    var entraineur: Entraineur? = null,
    expInit : Double
) {
    var niveau : Int = 1
    var attaque : Int = espece.baseAttaque + listOf(-2,2).random() /* score de base de l'espèce + -2..2 */
    var defense : Int = espece.baseDefense + listOf(-2,2).random() /* score de base de l'espèce + -2..2 */
    var vitesse : Int = espece.baseVitesse
    var attaqueSpe : Int = espece.baseAttaqueSpe
    var defenseSpe : Int = espece.baseDefenseSpe
    var pvMax : Int = espece.basePv + listOf(-5,5).random() /* le basePv de l’espece + ou - 5 */
    var potentiel : Double = Random.nextDouble(0.5, 2.0) /* un nombre entre 0,5 et 2 (compris) choisi aléatoirement */
    var exp : Double = 0.0
        get() = field
        set(value) {
            var estNiveau1 = (niveau == 1)

            while (field >= palierExp(niveau)) {
                levelUp()

                if (!estNiveau1) {
                    println("Le monstre $nom est maintenant niveau $niveau !")
                }
            }
        }


    var pv : Int = pvMax
        get() = field
        set(nouveauPv) {
            if (nouveauPv < 0) {
                field = 0
            } else if(nouveauPv > pvMax) {
                field = pvMax
            } else {
                field = nouveauPv
            }
        }

    /**
     * Calcule le palier d'expérience nécessaire pour atteindre un niveau donné.
     *
     * Le palier est calculé selon une formule quadratique :
     * 100 × (niveau - 1)².
     *
     * Plus le niveau recherché est élevé, plus la quantité d'expérience
     * nécessaire pour l'atteindre augmente rapidement.
     *
     * @param niveau Le niveau pour lequel le palier d'expérience doit être calculé.
     *
     * @return Le nombre de points d'expérience nécessaires pour atteindre
     *         le niveau demandé.
     */

    fun palierExp(niveau: Int): Double {
         var niv : Double = 100 * (this.niveau - 1).toDouble().pow(2)
        return niv
    }

    /**
     * Fait monter l'individu d'un niveau.
     *
     * Lors d'une montée de niveau, le niveau de l'individu est augmenté
     * de 1 et chacune de ses statistiques principales évolue.
     *
     * L'évolution des statistiques dépend des statistiques de base de son espèce
     * et de son potentiel individuel.
     *
     * Une variation aléatoire est également appliquée à certaines statistiques.
     */

    fun levelUp() {
        niveau++

        var evoAttaque = round(espece.baseAttaque * potentiel).toInt() + listOf(-2,2).random() /* score de base de l'espèce + -2..2 */
        var evoDefense = round(espece.baseDefense * potentiel).toInt() + listOf(-2,2).random() /* score de base de l'espèce + -2..2 */
        var evoVitesse = round(espece.baseVitesse * potentiel).toInt()
        var evoAttaqueSpe = round(espece.baseAttaqueSpe * potentiel).toInt()
        var evoDefenseSpe = round(espece.baseDefenseSpe * potentiel).toInt()
        var evoPvMax = round(espece.basePv *potentiel).toInt() + listOf(-5,5).random()

        attaque += evoAttaque
        defense += evoDefense
        vitesse += evoVitesse
        attaqueSpe += evoAttaqueSpe
        defenseSpe += evoDefenseSpe
        pvMax += evoPvMax
    }

    init {
        this.exp = expInit // applique le setter et déclenche un éventuel level-up
    }

    /**
     * Permet à l'individu d'attaquer un autre monstre.
     *
     * Les dégâts infligés sont calculés à partir de la statistique d'attaque
     * de l'individu attaquant et de la défense du monstre ciblé.
     *
     * Les points de vie de la cible sont ensuite diminués du montant
     * de dégâts calculé.
     *
     * Les dégâts infligés sont toujours d'au moins 1 point.
     *
     * @param cible Le monstre qui sera attaqué.
     */

    fun attaquer(cible : IndividuMonstres) {
        var degatBrut : Int = this.attaque
        var degatTotal : Int = degatBrut - (this.defense/2)

        if (degatTotal < 1) {
            degatTotal = 1
        }
        var pvAvant : Int = cible.pv
        cible.pv -= degatTotal
        var pvApres : Int = cible.pv

        println("${joueur}, inflige ${pvAvant-pvApres} dégats à ${cible.nom}")
    }

    /**
     * Permet de modifier le nom de l'individu.
     *
     * Le nouveau nom est demandé à l'utilisateur via la console.
     * Le nom actuel de l'individu est ensuite remplacé par le nom saisi.
     */

    fun renommer() {
        print("Renommer : ")
        var nouveauNom : String = readln()
        if (nouveauNom == null) {
            nouveauNom == ""
        } else {
            nom = nouveauNom
        }
    }

    /**
     * Affiche les informations détaillées de l'individu.
     *
     * Les informations affichées comprennent notamment :
     * - sa représentation artistique ;
     * - son nom ;
     * - son niveau ;
     * - son expérience ;
     * - ses points de vie actuels et maximums ;
     * - son attaque ;
     * - sa défense ;
     * - sa vitesse ;
     * - son attaque spéciale ;
     * - sa défense spéciale.
     *
     * La représentation artistique est obtenue à partir de l'espèce
     * à laquelle appartient l'individu.
     */

    fun afficheDetail() {
        espece.afficheArt()

        println("=========================")
        println("Nom : ${this.nom}       Niveau : ${this.niveau}")
        println("Exp : ${this.exp}")
        println("PV : ${this.pv}/${this.pvMax}")
        println("=========================")
        println("Atq : ${this.attaque}   Def : ${this.defense}    Vitesse : ${this.vitesse}")
        println("AtqSpe ${this.attaqueSpe}   DefSpe ${this.defenseSpe}")

    }

}

