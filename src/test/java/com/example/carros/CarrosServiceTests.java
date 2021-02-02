package com.example.carros;

import com.example.carros.domain.Carro;
import com.example.carros.domain.CarroService;
import com.example.carros.domain.dto.CarroDTO;
import com.example.carros.api.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@SpringBootTest
class CarrosServiceTests {

	@Autowired
	private CarroService service;

	@Test
	public void testSave() {
		Carro carro = new Carro();
		carro.setNome("Novo carro");
		carro.setTipo("Esportivos");

		CarroDTO c = service.insert(carro);

		// Verifica se está nulo, se estiver falha.
		assertNotNull(c);

		Long id = c.getId();
		assertNotNull(id);

		//Busca o objeto
		c = service.getCarrosById(id);
		assertNotNull(c);

		assertEquals("Novo carro", c.getNome());
		assertEquals("Esportivos", c.getTipo());

		//Deletar o objeto
		service.delete(id);

		//Verificar se deletou
		try {
			assertNull(service.getCarrosById(id));
			fail("O carro nao foi excluido");
		} catch (ObjectNotFoundException ex){
			// OK
		};

	}

	@Test
	public void testLista() {

		List<CarroDTO> carros = service.getCarros();

		assertEquals(30, carros.size());
	}

	@Test
	public void testGet() {

		CarroDTO c = service.getCarrosById(11L);
		assertNotNull(c);
		assertEquals("Ferrari FF", c.getNome());

	}

	@Test
	public void testeListPorTipo() {
	
		assertEquals(10, service.getCarrosByTipo("classicos").size());
		assertEquals(10, service.getCarrosByTipo("esportivos").size());
		assertEquals(10, service.getCarrosByTipo("luxo").size());

	}

	@Test
	public void encoder(){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("123456"));
	}
}
