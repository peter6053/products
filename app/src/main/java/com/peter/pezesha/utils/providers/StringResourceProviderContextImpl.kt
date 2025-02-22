package com.peter.pezesha.utils.providers

import android.content.Context
import com.pezesha.R


class StringResourceProviderContextImpl(context: Context) : StringResourceProvider {

    private val resources = context.resources

    override fun getDummyJsonApiBaseUrl(): String = resources.getString(R.string.dummyjson_api_base_url)
}
