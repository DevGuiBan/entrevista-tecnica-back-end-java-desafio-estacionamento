package br.com.ada.estacionamento.carros;

import br.com.ada.estacionamento.vagas.Vaga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarroService {


    public CarroRepository carroRepository;

    public CarroService(CarroRepository carroRepository) {
        this.carroRepository = carroRepository;
    }

    public void cadastrarCarro(Carro carro) {
        carroRepository.save(carro);
    }

    public void estacionar(Carro carro) {
        if (carro.getVaga() != null) {
            throw new IllegalStateException("O carro já está estacionado.");
        }

        Vaga vaga = carro.getVaga();
        if (vaga == null || vaga.isOcupada()) {
            throw new IllegalArgumentException("A vaga está ocupada ou não foi informada.");
        }

        vaga.setOcupada(true);
        carro.setVaga(vaga);
        carroRepository.save(carro);
    }

    public void retirarCarro(String placa) {
        Optional<Carro> carroOptional = carroRepository.findById(placa);
        if (carroOptional.isEmpty()) {
            throw new IllegalArgumentException("Carro não encontrado.");
        }

        Carro carro = carroOptional.get();

        Vaga vaga = carro.getVaga();
        if (vaga == null) {
            throw new IllegalStateException("O carro não está estacionado.");
        }

        vaga.setOcupada(false);
        carro.setVaga(null);
        carroRepository.save(carro);
    }



}
