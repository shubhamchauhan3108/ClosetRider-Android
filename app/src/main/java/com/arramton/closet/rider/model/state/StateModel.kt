package com.arramton.closet.rider.model.state
data class stateList(var states: MutableList<StateModel>)
data class StateModel(
    val name:String,
    val cities:List<Cities>
)

data class Cities(
    val name:String,
)

