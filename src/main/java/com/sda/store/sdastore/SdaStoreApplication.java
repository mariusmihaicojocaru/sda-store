package com.sda.store.sdastore;

import com.sda.store.sdastore.model.Role;
import com.sda.store.sdastore.model.RoleEnum;
import com.sda.store.sdastore.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SdaStoreApplication implements CommandLineRunner {

	private RoleService roleService;

	public SdaStoreApplication(RoleService roleService){
		this.roleService = roleService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SdaStoreApplication.class, args);
	}

	@Override
	public void run(String... args) {
		List<RoleEnum> roleEnumList = Arrays.asList(RoleEnum.values());
		for(RoleEnum roleEnum: roleEnumList){
			Role role = new Role(roleEnum.name());
			roleService.create(role);
		}
	}
}
