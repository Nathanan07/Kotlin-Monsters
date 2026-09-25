package monstre

import java.io.File

/**
 * Représente une espèce de Monstre dans le contexte du jeu.
 *
 * Les monstres sont des créatures : ils peuvent être capturés,
 *  * possèdent chacun leurs propres caractéristiques et peuvent être utilisés pour combattre.
 *
 * @property id L'identifiant unique du monstre.
 * @property nom Le nom du monstre.
 * @property type Le type ou l'élément auquel appartient le monstre.
 * @property baseAttaque La valeur d'attaque de base du monstre.
 * @property baseDefense La valeur de défense de base du monstre.
 * @property baseVitesse La valeur de vitesse de base du monstre.
 * @property baseAttaqueSpe La valeur d'attaque spéciale de base du monstre.
 * @property baseDefenseSpe La valeur de défense spéciale de base du monstre.
 * @property basePv Le nombre de points de vie de base du monstre.
 * @property modAttaque Le modificateur appliqué à l'attaque de base du monstre.
 * @property modDefense Le modificateur appliqué à la défense de base du monstre.
 * @property modVitesse Le modificateur appliqué à la vitesse de base du monstre.
 * @property modAttaqueSpe Le modificateur appliqué à l'attaque spéciale de base du monstre.
 * @property modDefenseSpe Le modificateur appliqué à la défense spéciale de base du monstre.
 * @property modPv Le modificateur appliqué aux points de vie de base du monstre.
 * @property description La description générale du monstre et de ses caractéristiques.
 * @property particularites Les particularités propres au monstre, telles que ses capacités ou ses caractéristiques spéciales.
 * @property caractères Les traits de caractère et la personnalité du monstre.
 *
 */


class EspeceMonstre (
    val id : Int,
    val nom: String,
    val type: String,
    val baseAttaque: Int,
    val baseDefense: Int,
    val baseVitesse: Int,
    val baseAttaqueSpe: Int,
    val baseDefenseSpe: Int,
    val basePv: Int,
    val modAttaque: Double,
    val modDefense: Double,
    val modVitesse: Double,
    val modAttaqueSpe: Double,
    val modDefenseSpe: Double,
    val modPv: Double,
    val description: String = "",
    val particularites: String = "",
    val caractères: String = "",
) {
    /**
     * Affiche la représentation artistique ASCII du monstre.
     *
     * @param deFace Détermine si l'art affiché est de face (true) ou de dos (false).
     *               La valeur par défaut est true.
     * @return Une chaîne de caractères contenant l'art ASCII du monstre avec les codes couleur ANSI.
     *         L'art est lu à partir d'un fichier texte dans le dossier resources/art.
     */
    fun afficheArt(deFace: Boolean=true): String{
        val nomFichier = if(deFace) "front" else "back";
        val art=
            File("src/main/resources/art/${this.nom.lowercase()}/$nomFichier.txt").readText()
        val safeArt = art.replace("/", "∕")
        return safeArt.replace("\\u001B", "\u001B")
    }

}