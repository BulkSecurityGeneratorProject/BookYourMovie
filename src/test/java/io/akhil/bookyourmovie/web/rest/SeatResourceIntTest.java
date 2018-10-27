package io.akhil.bookyourmovie.web.rest;

import io.akhil.bookyourmovie.BookYourMovieApp;

import io.akhil.bookyourmovie.domain.Seat;
import io.akhil.bookyourmovie.repository.SeatRepository;
import io.akhil.bookyourmovie.service.SeatService;
import io.akhil.bookyourmovie.service.dto.SeatDTO;
import io.akhil.bookyourmovie.service.mapper.SeatMapper;
import io.akhil.bookyourmovie.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static io.akhil.bookyourmovie.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.akhil.bookyourmovie.domain.enumeration.Status;
/**
 * Test class for the SeatResource REST controller.
 *
 * @see SeatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookYourMovieApp.class)
public class SeatResourceIntTest {

    private static final String DEFAULT_SEAT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SEAT_NUMBER = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.BOOKED;
    private static final Status UPDATED_STATUS = Status.VACANT;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatMapper seatMapper;
    
    @Autowired
    private SeatService seatService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSeatMockMvc;

    private Seat seat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SeatResource seatResource = new SeatResource(seatService);
        this.restSeatMockMvc = MockMvcBuilders.standaloneSetup(seatResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Seat createEntity(EntityManager em) {
        Seat seat = new Seat()
            .seatNumber(DEFAULT_SEAT_NUMBER)
            .status(DEFAULT_STATUS);
        return seat;
    }

    @Before
    public void initTest() {
        seat = createEntity(em);
    }

    @Test
    @Transactional
    public void createSeat() throws Exception {
        int databaseSizeBeforeCreate = seatRepository.findAll().size();

        // Create the Seat
        SeatDTO seatDTO = seatMapper.toDto(seat);
        restSeatMockMvc.perform(post("/api/seats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatDTO)))
            .andExpect(status().isCreated());

        // Validate the Seat in the database
        List<Seat> seatList = seatRepository.findAll();
        assertThat(seatList).hasSize(databaseSizeBeforeCreate + 1);
        Seat testSeat = seatList.get(seatList.size() - 1);
        assertThat(testSeat.getSeatNumber()).isEqualTo(DEFAULT_SEAT_NUMBER);
        assertThat(testSeat.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createSeatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = seatRepository.findAll().size();

        // Create the Seat with an existing ID
        seat.setId(1L);
        SeatDTO seatDTO = seatMapper.toDto(seat);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSeatMockMvc.perform(post("/api/seats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Seat in the database
        List<Seat> seatList = seatRepository.findAll();
        assertThat(seatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSeats() throws Exception {
        // Initialize the database
        seatRepository.saveAndFlush(seat);

        // Get all the seatList
        restSeatMockMvc.perform(get("/api/seats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(seat.getId().intValue())))
            .andExpect(jsonPath("$.[*].seatNumber").value(hasItem(DEFAULT_SEAT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getSeat() throws Exception {
        // Initialize the database
        seatRepository.saveAndFlush(seat);

        // Get the seat
        restSeatMockMvc.perform(get("/api/seats/{id}", seat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(seat.getId().intValue()))
            .andExpect(jsonPath("$.seatNumber").value(DEFAULT_SEAT_NUMBER.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSeat() throws Exception {
        // Get the seat
        restSeatMockMvc.perform(get("/api/seats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSeat() throws Exception {
        // Initialize the database
        seatRepository.saveAndFlush(seat);

        int databaseSizeBeforeUpdate = seatRepository.findAll().size();

        // Update the seat
        Seat updatedSeat = seatRepository.findById(seat.getId()).get();
        // Disconnect from session so that the updates on updatedSeat are not directly saved in db
        em.detach(updatedSeat);
        updatedSeat
            .seatNumber(UPDATED_SEAT_NUMBER)
            .status(UPDATED_STATUS);
        SeatDTO seatDTO = seatMapper.toDto(updatedSeat);

        restSeatMockMvc.perform(put("/api/seats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatDTO)))
            .andExpect(status().isOk());

        // Validate the Seat in the database
        List<Seat> seatList = seatRepository.findAll();
        assertThat(seatList).hasSize(databaseSizeBeforeUpdate);
        Seat testSeat = seatList.get(seatList.size() - 1);
        assertThat(testSeat.getSeatNumber()).isEqualTo(UPDATED_SEAT_NUMBER);
        assertThat(testSeat.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingSeat() throws Exception {
        int databaseSizeBeforeUpdate = seatRepository.findAll().size();

        // Create the Seat
        SeatDTO seatDTO = seatMapper.toDto(seat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSeatMockMvc.perform(put("/api/seats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(seatDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Seat in the database
        List<Seat> seatList = seatRepository.findAll();
        assertThat(seatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSeat() throws Exception {
        // Initialize the database
        seatRepository.saveAndFlush(seat);

        int databaseSizeBeforeDelete = seatRepository.findAll().size();

        // Get the seat
        restSeatMockMvc.perform(delete("/api/seats/{id}", seat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Seat> seatList = seatRepository.findAll();
        assertThat(seatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Seat.class);
        Seat seat1 = new Seat();
        seat1.setId(1L);
        Seat seat2 = new Seat();
        seat2.setId(seat1.getId());
        assertThat(seat1).isEqualTo(seat2);
        seat2.setId(2L);
        assertThat(seat1).isNotEqualTo(seat2);
        seat1.setId(null);
        assertThat(seat1).isNotEqualTo(seat2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SeatDTO.class);
        SeatDTO seatDTO1 = new SeatDTO();
        seatDTO1.setId(1L);
        SeatDTO seatDTO2 = new SeatDTO();
        assertThat(seatDTO1).isNotEqualTo(seatDTO2);
        seatDTO2.setId(seatDTO1.getId());
        assertThat(seatDTO1).isEqualTo(seatDTO2);
        seatDTO2.setId(2L);
        assertThat(seatDTO1).isNotEqualTo(seatDTO2);
        seatDTO1.setId(null);
        assertThat(seatDTO1).isNotEqualTo(seatDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(seatMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(seatMapper.fromId(null)).isNull();
    }
}
