plugins {
	id("fabric-loom") version ("1.6-SNAPSHOT")
}

version = properties["mod_version"]!! as String
group = properties["maven_group"]!! as String

base {
	archivesName = properties["archives_base_name"]!! as String
}

repositories {
}

loom {
    splitEnvironmentSourceSets()

	mods {
		create("letmeteleport") {
			sourceSet(sourceSets["main"])
		}
	}

	accessWidenerPath = file("src/main/resources/letmeteleport.accesswidener")
}

dependencies {
	minecraft("com.mojang:minecraft:${properties["minecraft_version"]}")
    mappings("net.fabricmc:yarn:${properties["yarn_mappings"]}:v2")
    modImplementation("net.fabricmc:fabric-loader:${properties["loader_version"]}")
	
}

tasks {
	processResources {
		inputs.property("version", project.version)

		filesMatching("fabric.mod.json") {
			expand(mapOf("version" to project.version))
		}
	}

	withType<JavaCompile>() {
		options.encoding = "UTF-8"
		options.release.set(17)
	}

	withType<Jar> {
		from(rootProject.file("LICENSE.md"))
	}
}


java {
	withSourcesJar()

	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
}
