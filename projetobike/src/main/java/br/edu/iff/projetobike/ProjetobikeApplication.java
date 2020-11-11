package br.edu.iff.projetobike;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.edu.iff.projetobike.model.Bicicleta;
import br.edu.iff.projetobike.model.Cliente;
import br.edu.iff.projetobike.model.Reserva;
import br.edu.iff.projetobike.model.TipoBicicletaEnum;
import br.edu.iff.projetobike.model.TipoCartaoCreditoEnum;
import br.edu.iff.projetobike.repository.BicicletaRepository;
import br.edu.iff.projetobike.repository.ClienteRepository;
import br.edu.iff.projetobike.repository.ReservaRepository;

@SpringBootApplication
public class ProjetobikeApplication implements CommandLineRunner {

	@Autowired
	private BicicletaRepository bicicletaRepo;

	@Autowired
	private ClienteRepository clienteRepo;

	@Autowired
	private ReservaRepository reservaRepo;

	public static void main(String[] args) {
		SpringApplication.run(ProjetobikeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Cliente
		Cliente c1 = new Cliente();
		c1.setNome("Lucas");
		c1.setSobrenome("Schimith");
		c1.setCpf("016.853.400-24");
		c1.setEmail("lucas@gmail.com");
		c1.setSenha("1234567");
		c1.setTipoCartaoCredito(TipoCartaoCreditoEnum.VISA);
		c1.setCartaoCreditoNo("4024007135540408");
		c1.setCodCartaoCredito(676);
		c1.setValidCartaoCreditoMes(07);
		c1.setValidCartaoCreditoAno(2022);

		clienteRepo.save(c1);

		// Bicicleta
		Bicicleta b1 = new Bicicleta();
		b1.setPreco((float) 5.99);
		b1.setTipo(TipoBicicletaEnum.STANDART);

		Bicicleta b2 = new Bicicleta();
		b1.setPreco((float) 10.99);
		b1.setTipo(TipoBicicletaEnum.PREMIUM);

		bicicletaRepo.save(b1);
		bicicletaRepo.save(b2);

		// Reserva
		Reserva r1 = new Reserva();
		r1.setCliente(c1);
		r1.setBicicletas(List.of(b1, b2));

		Calendar horaInicio = Calendar.getInstance();
		Calendar horaFim = Calendar.getInstance();
		horaInicio.set(2020, 11, 10, 12, 00, 00);
		horaFim.set(2020, 11, 10, 20, 00, 00);

		r1.setHoraInicio(horaInicio);
		r1.setHoraFim(horaInicio);

		reservaRepo.save(r1);
	}

}
