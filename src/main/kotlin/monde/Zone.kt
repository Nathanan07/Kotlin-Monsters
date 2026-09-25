package monde

import monstre.EspeceMonstre

class Zone (
    val id : Int,
    val nom : String,
    val expZone : Int,
    val especesMonstres: MutableList<EspeceMonstre> = mutableListOf(),
    var zoneSuivante: Zone? = null,
    var zonePrecedante: Zone? = null

    // TODO genereMonstre()
    // TODO rencontreMonstre()

)