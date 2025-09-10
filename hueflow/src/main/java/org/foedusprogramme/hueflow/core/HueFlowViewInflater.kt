package org.foedusprogramme.hueflow.core

import androidx.appcompat.app.AppCompatViewInflater
import com.google.android.material.theme.MaterialComponentsViewInflater

open class HueFlowViewInflater(parent: AppCompatViewInflater) :
	CallbackViewInflater(parent, HueFlowViewInflaterCallback())

class MaterialHueFlowViewInflater() : HueFlowViewInflater(MaterialComponentsViewInflater())