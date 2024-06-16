architectury {
    val enabledPlatforms: String by rootProject
    common(enabledPlatforms.split(","))
}

dependencies {
    modCompileOnly(group = "tech.thatgravyboat", name = "commonats", version = "3.0")
}