plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "MSAEvent"
include("user")
include("eureka")
include("event")
include("gateway")
include("common")
