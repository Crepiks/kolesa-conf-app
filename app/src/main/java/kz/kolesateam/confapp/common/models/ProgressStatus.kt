package kz.kolesateam.confapp.common.models

sealed class ProgressStatus {

    object Loading : ProgressStatus()
    object Finished : ProgressStatus()
}
