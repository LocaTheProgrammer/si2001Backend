package it.si2001.converter;

import it.si2001.dao.CarRepository;
import it.si2001.dao.ReservationRepository;
import it.si2001.dao.UserRepository;
import it.si2001.dto.CarDTO;
import it.si2001.dto.ReservationDTO;
import it.si2001.dto.UserDTO;
import it.si2001.entity.Car;
import it.si2001.entity.Reservation;
import it.si2001.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EntityDTOConverter {


    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final ReservationRepository reservationRepository;

    public final static String pattern = "yyyy-MM-dd";
    public final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    private static final Logger log = LoggerFactory.getLogger(EntityDTOConverter.class);



    public EntityDTOConverter(UserRepository userRepository, CarRepository carRepository, ReservationRepository reservationRepository){
        super();
        this.userRepository=userRepository;
        this.carRepository=carRepository;
        this.reservationRepository=reservationRepository;
    }




    public Car carDtoToCarEntity(CarDTO carDTO) throws ParseException {
        Car c= new Car();

        Date carDate=simpleDateFormat.parse(carDTO.getYear());

        c.setCylinders(carDTO.getCylinders());
        c.setYear(carDate);
        c.setDisplacement(carDTO.getDisplacement());
        c.setOrigin(carDTO.getOrigin());
        c.setHorsePower(carDTO.getHorsePower());
        c.setName(carDTO.getName());
        c.setMilesPerGallon(carDTO.getMilesPerGallon());
        c.setWeightInLbs(carDTO.getWeightInLbs());
        c.setAcceleration(carDTO.getAcceleration());

        return c;
    }

    public CarDTO carEntityToCarDTO(Car car){
        CarDTO carDTO = new CarDTO();

        String carDTOYear= simpleDateFormat.format(car.getYear());

        carDTO.setId(car.getId());
        carDTO.setAcceleration(car.getAcceleration());
        carDTO.setCylinders(car.getCylinders());
        carDTO.setWeightInLbs(car.getWeightInLbs());
        carDTO.setYear(carDTOYear);
        carDTO.setDisplacement(car.getDisplacement());
        carDTO.setHorsePower(car.getHorsePower());
        carDTO.setOrigin(car.getOrigin());
        carDTO.setName(car.getName());
        carDTO.setMilesPerGallon(car.getMilesPerGallon());

        return carDTO;
    }


    public ReservationDTO reservationEntityToReservationDTO(Reservation reservation){
        ReservationDTO r = new ReservationDTO();



        r.setUserId(reservation.getUser().getId());
        r.setCarId(reservation.getCar().getId());
        r.setId(reservation.getId());
        r.setToDate(reservation.getToDate());
        r.setFromDate(reservation.getFromDate());

        return r;
    }


    public Reservation reservationDtoToReservationEntity(ReservationDTO reservationDTO){
        Reservation r = new Reservation();

        log.info("reservation dto: "+reservationDTO.toString());

        User u=this.userRepository.findById(reservationDTO.getUserId()).get();
        Car c=this.carRepository.findById(reservationDTO.getCarId()).get();

        r.setUser(u);
        r.setCar(c);
        r.setId(reservationDTO.getId());
        r.setToDate(reservationDTO.getToDate());
        r.setFromDate(reservationDTO.getFromDate());

        return r;
    }

    public User userDtoToUserEntity(UserDTO userDTO){
        User u = new User();

        List<Reservation> r= this.reservationRepository.findByUserId(userDTO.getId());

        u.setReservationList(r);
        u.setEmail(userDTO.getEmail());
        u.setId(userDTO.getId());
        u.setFirstName(userDTO.getFirstName());
        u.setPassword(userDTO.getPassword());
        u.setLastName(userDTO.getLastName());
        u.setRole(userDTO.getRole());

        return u;

    }

    public UserDTO userEntityToUserDTO(User user){
        UserDTO u = new UserDTO();

//        u.setIdReservationList(user.getReservationList());
        u.setEmail(user.getEmail());
        u.setId(user.getId());
        u.setFirstName(user.getFirstName());
        u.setPassword(user.getPassword());
        u.setLastName(user.getLastName());
        u.setRole(user.getRole());

        return u;
    }


}
