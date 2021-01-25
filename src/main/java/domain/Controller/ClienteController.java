package domain.Controller;


import domain.entity.Cliente;
import domain.repository.ClienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Cliente> listar(){
        return clienteRepository.findAll();
    }

    @PostMapping
    @ResponseStatus (HttpStatus.CREATED)
    public Cliente save(@RequestBody Cliente cliente){
        return clienteRepository.save(cliente);
    }

    @DeleteMapping ("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById (@RequestBody Cliente cliente,@PathVariable Integer id) {
        clienteRepository.deleteById(cliente);

    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cliente getClienteById(@PathVariable Integer id){
        return clienteRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente nao encontrado"));
        }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update (@RequestBody Cliente cliente, @PathVariable Integer id){
        clienteRepository.findById(id)
                        .map(clienteExistente ->{
                            cliente.setId(clienteExistente.getId());
                            clienteRepository.save(cliente);
                            return clienteExistente;
                        }) .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente nao encontrado"));

    }
}
