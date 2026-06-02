package com.tecsup.petclinic.util;

import com.tecsup.petclinic.dtos.PetDTO;
import com.tecsup.petclinic.dtos.PetCountByTypeDTO;
import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.entities.Pet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TObjectCreator {

	public static Pet getPet() {
		return new Pet(1,"Leo",1,1, null);
	}

	public static Pet newPet() {
		Pet newEntity = new Pet();
		newEntity.setName("Punky");
		newEntity.setTypeId(1);
		newEntity.setOwnerId(1);
		return newEntity;
	}

	public static Pet newPetCreated() {
		Pet pet = newPet();
		pet.setId(1000);
		return pet;
	}

	public static PetDTO newPetDTO() {
		PetDTO newDTO = new PetDTO();
		newDTO.setName("Punky");
		newDTO.setTypeId(1);
		newDTO.setOwnerId(1);
		return newDTO;
	}

	public static PetDTO newPetDTOCreated() {
		PetDTO petDTO = newPetDTO();
		petDTO.setId(1000);
		return petDTO;
	}


	public static Pet newPetForUpdate() {
		return new Pet(0,"Bear",1,1,null);
	}

	public static Pet newPetCreatedForUpdate() {
		Pet pet = newPetForUpdate();
		pet.setId(4000);
		return pet;
	}

	public static Pet newPetForDelete() {
		return new Pet(0,"Bird",1,1, null);
	}

	public static Pet newPetCreatedForDelete() {
		Pet pet = newPetForDelete();
		pet.setId(2000);
		return pet;
	}

	public static List<Pet> getPetsForFindByName() {
		List<Pet> pets  = new ArrayList<Pet>();
		pets.add(new Pet(1,"Leo",1,1, null));
		return pets;
	}

	public static List<Pet> getPetsForFindByTypeId() {
		List<Pet> pets  = new ArrayList<Pet>();
		pets.add(new Pet(9,"Lucky",5,7, null));
		pets.add(new Pet(11,"Freddy",5,9, null));
		return pets;
	}

	public static List<Pet> getPetsForFindByOwnerId() {
		List<Pet> pets  = new ArrayList<Pet>();
		pets.add(new Pet(12,"Lucky",2,10, null));
		pets.add(new Pet(13,"Sly",1,10, null));
		return pets;
	}

	public static TypeDTO getTypeCat() {
		return TypeDTO.builder()
				.id(1)
				.name("cat")
				.description("Domestic feline")
				.active(true)
				.sizeCategory("small")
				.averageLifespan(15)
				.careLevel("medium")
				.build();
	}

	public static TypeDTO newTypeDTO() {
		return TypeDTO.builder()
				.name("ferret")
				.description("Domestic ferret")
				.active(true)
				.sizeCategory("small")
				.averageLifespan(8)
				.careLevel("medium")
				.build();
	}

	public static TypeDTO newTypeDTOCreated() {
		TypeDTO typeDTO = newTypeDTO();
		typeDTO.setId(1000);
		return typeDTO;
	}

	public static TypeDTO newTypeDTOForDelete() {
		return TypeDTO.builder()
				.name("axolotl")
				.description("Aquatic salamander")
				.active(true)
				.sizeCategory("small")
				.averageLifespan(15)
				.careLevel("high")
				.build();
	}

	public static TypeDTO newTypeDTOCreatedForDelete() {
		TypeDTO typeDTO = newTypeDTOForDelete();
		typeDTO.setId(2000);
		return typeDTO;
	}

	public static List<TypeDTO> getTypes() {
		return Arrays.asList(
				getTypeCat(),
				TypeDTO.builder().id(2).name("dog").description("Domestic canine").active(true).sizeCategory("medium").averageLifespan(12).careLevel("medium").build(),
				TypeDTO.builder().id(3).name("lizard").description("Reptile").active(true).sizeCategory("small").averageLifespan(10).careLevel("high").build(),
				TypeDTO.builder().id(4).name("bird").description("Avian").active(true).sizeCategory("small").averageLifespan(8).careLevel("medium").build(),
				TypeDTO.builder().id(5).name("hamster").description("Small rodent").active(true).sizeCategory("small").averageLifespan(3).careLevel("low").build(),
				TypeDTO.builder().id(6).name("snake").description("Serpent").active(false).sizeCategory("medium").averageLifespan(20).careLevel("high").build(),
				TypeDTO.builder().id(7).name("rabbit").description("Domestic rabbit").active(true).sizeCategory("small").averageLifespan(9).careLevel("medium").build(),
				TypeDTO.builder().id(8).name("turtle").description("Turtle or tortoise").active(true).sizeCategory("small").averageLifespan(30).careLevel("medium").build()
		);
	}

	public static List<TypeDTO> getActiveTypes() {
		List<TypeDTO> activeTypes = new ArrayList<TypeDTO>();
		for (TypeDTO typeDTO : getTypes()) {
			if (Boolean.TRUE.equals(typeDTO.getActive())) {
				activeTypes.add(typeDTO);
			}
		}
		return activeTypes;
	}

	public static List<PetCountByTypeDTO> getPetCountByType() {
		return Arrays.asList(
				PetCountByTypeDTO.builder().typeName("cat").petCount(4L).build(),
				PetCountByTypeDTO.builder().typeName("dog").petCount(4L).build(),
				PetCountByTypeDTO.builder().typeName("lizard").petCount(1L).build(),
				PetCountByTypeDTO.builder().typeName("bird").petCount(1L).build(),
				PetCountByTypeDTO.builder().typeName("hamster").petCount(2L).build(),
				PetCountByTypeDTO.builder().typeName("rabbit").petCount(0L).build(),
				PetCountByTypeDTO.builder().typeName("turtle").petCount(0L).build()
		);
	}
}
