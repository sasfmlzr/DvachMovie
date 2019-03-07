package dvachmovie.architecture.base

interface RuntimePermissions {
    fun request(permissions: List<String>)
    fun request(permission: String)

}
