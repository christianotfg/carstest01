package com.example.carros.domain;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.example.carros.domain.dto.CarroDTO;
import com.example.carros.api.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class CarroService {

    @Autowired
    private CarroRepository rep;

    public List<CarroDTO> getCarros() {
        List<Carro> carros = rep.findAll();
        List<CarroDTO> list = carros.stream().map(CarroDTO::create).collect(Collectors.toList());

        return list;

    }

    public CarroDTO getCarrosById(Long id) {
        Optional<Carro> carro = rep.findById(id);
        return carro.map(CarroDTO::create).orElseThrow(() -> new ObjectNotFoundException("Carro nao encontrado"));
    }

    public List<CarroDTO> getCarrosByTipo(String tipo) {
        return rep.findByTipo(tipo).stream().map(CarroDTO::create).collect(Collectors.toList());
    }

    public CarroDTO insert(Carro carro) {
        Assert.isNull(carro.getId(), "Não foi possível inserir o registro");
        return CarroDTO.create(rep.save(carro));

    }

    public CarroDTO update(Carro carro, Long id) {
        Assert.notNull(id, "Não foi possível atualizar o registro");

        // Busca o carro no banco
        Optional<Carro> optional = rep.findById(id);
        if (optional.isPresent()) {
            Carro db = optional.get();
            // Copiar as propriedades
            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());

            // Atualiza o carro
            rep.save(db);

            return CarroDTO.create(db);

        } else {
            return null;
            //throw new RuntimeException("Não foi possível atualizar o registro");
        }

    }

    public void delete(Long id) {
        rep.deleteById(id);

    }

}