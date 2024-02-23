package com.rafalesan.credikiosko

import com.rafalesan.credikiosko.core.commons.data.build_config_provider.BuildConfigFields
import com.rafalesan.credikiosko.core.commons.data.build_config_provider.BuildConfigFieldsProvider

class ApplicationBuildConfigFieldsProvider : BuildConfigFieldsProvider {

    override fun get() = BuildConfigFields(
        buildType = BuildConfig.BUILD_TYPE,
        versionCode = BuildConfig.VERSION_CODE,
        versionName = BuildConfig.VERSION_NAME
    )

}
