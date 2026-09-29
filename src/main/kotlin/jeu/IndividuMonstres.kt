package jeu

import dressseur.Entraineur
import joueur
import monstre.EspeceMonstre
import kotlin.math.pow
import kotlin.math.round
import kotlin.random.Random

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

    fun palierExp(niveau: Int): Double {
         var niv : Double = 100 * (this.niveau - 1).toDouble().pow(2)
        return niv
    }

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

    fun renommer() {
        print("Renommer : ")
        var nouveauNom : String = readln()
        if (nouveauNom == null) {
            nouveauNom == ""
        } else {
            nom = nouveauNom
        }
    }

    fun afficheDetail() {
        espece.afficheArt()

        println("=========================")
        println("Nom : ${nom}       Niveau : ${niveau}")
        println("Exp : ${exp}")
        println("PV : ${pv}/${pvMax}")
        println("=========================")
        println("Atq : ${attaque}   Def : ${defense}    Vitesse : ${vitesse}")
        println("AtqSpe ${attaqueSpe}   DefSpe ${defenseSpe}")

    }

}

