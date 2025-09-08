package org.foedusprogramme.hueflow.palette

import com.google.android.material.R

enum class ColorToken {
    Surface,
    Primary,
    PrimaryContainer,
    OnPrimary,
    OnPrimaryContainer;

    companion object {
        val attrToTokenMapping = mapOf(
            R.attr.colorSurface to Surface,
            R.attr.colorPrimary to Primary,
            R.attr.colorPrimaryContainer to PrimaryContainer,
            R.attr.colorOnPrimary to OnPrimary,
            R.attr.colorOnPrimaryContainer to OnPrimaryContainer,
        )
    }
}