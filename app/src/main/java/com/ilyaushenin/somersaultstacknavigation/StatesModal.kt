package com.ilyaushenin.somersaultstacknavigation

import com.ilyaushenin.somersault.SomersaultStackNavigation

data class StatesModal(
    /***
     * Init SomersaultStackNavigation in your UDF state class ***/
    val somersaultStackNavigation: SomersaultStackNavigation
    = SomersaultStackNavigation("BoxC")
)