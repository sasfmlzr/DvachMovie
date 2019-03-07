package dvachmovie.architecture.base

interface PermissionsCallback {
    fun onPermissionsGranted(permissions: List<String>)
}
