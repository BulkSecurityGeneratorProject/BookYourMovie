package io.akhil.bookyourmovie.web.rest;

import io.akhil.bookyourmovie.BookYourMovieApp;

import io.akhil.bookyourmovie.domain.Theatre;
import io.akhil.bookyourmovie.repository.TheatreRepository;
import io.akhil.bookyourmovie.service.TheatreService;
import io.akhil.bookyourmovie.service.dto.TheatreDTO;
import io.akhil.bookyourmovie.service.mapper.TheatreMapper;
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

/**
 * Test class for the TheatreResource REST controller.
 *
 * @see TheatreResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookYourMovieApp.class)
public class TheatreResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AREA = "AAAAAAAAAA";
    private static final String UPDATED_AREA = "BBBBBBBBBB";

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private TheatreMapper theatreMapper;
    
    @Autowired
    private TheatreService theatreService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTheatreMockMvc;

    private Theatre theatre;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TheatreResource theatreResource = new TheatreResource(theatreService);
        this.restTheatreMockMvc = MockMvcBuilders.standaloneSetup(theatreResource)
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
    public static Theatre createEntity(EntityManager em) {
        Theatre theatre = new Theatre()
            .name(DEFAULT_NAME)
            .area(DEFAULT_AREA);
        return theatre;
    }

    @Before
    public void initTest() {
        theatre = createEntity(em);
    }

    @Test
    @Transactional
    public void createTheatre() throws Exception {
        int databaseSizeBeforeCreate = theatreRepository.findAll().size();

        // Create the Theatre
        TheatreDTO theatreDTO = theatreMapper.toDto(theatre);
        restTheatreMockMvc.perform(post("/api/theatres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(theatreDTO)))
            .andExpect(status().isCreated());

        // Validate the Theatre in the database
        List<Theatre> theatreList = theatreRepository.findAll();
        assertThat(theatreList).hasSize(databaseSizeBeforeCreate + 1);
        Theatre testTheatre = theatreList.get(theatreList.size() - 1);
        assertThat(testTheatre.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTheatre.getArea()).isEqualTo(DEFAULT_AREA);
    }

    @Test
    @Transactional
    public void createTheatreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = theatreRepository.findAll().size();

        // Create the Theatre with an existing ID
        theatre.setId(1L);
        TheatreDTO theatreDTO = theatreMapper.toDto(theatre);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTheatreMockMvc.perform(post("/api/theatres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(theatreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Theatre in the database
        List<Theatre> theatreList = theatreRepository.findAll();
        assertThat(theatreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = theatreRepository.findAll().size();
        // set the field null
        theatre.setName(null);

        // Create the Theatre, which fails.
        TheatreDTO theatreDTO = theatreMapper.toDto(theatre);

        restTheatreMockMvc.perform(post("/api/theatres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(theatreDTO)))
            .andExpect(status().isBadRequest());

        List<Theatre> theatreList = theatreRepository.findAll();
        assertThat(theatreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAreaIsRequired() throws Exception {
        int databaseSizeBeforeTest = theatreRepository.findAll().size();
        // set the field null
        theatre.setArea(null);

        // Create the Theatre, which fails.
        TheatreDTO theatreDTO = theatreMapper.toDto(theatre);

        restTheatreMockMvc.perform(post("/api/theatres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(theatreDTO)))
            .andExpect(status().isBadRequest());

        List<Theatre> theatreList = theatreRepository.findAll();
        assertThat(theatreList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTheatres() throws Exception {
        // Initialize the database
        theatreRepository.saveAndFlush(theatre);

        // Get all the theatreList
        restTheatreMockMvc.perform(get("/api/theatres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(theatre.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].area").value(hasItem(DEFAULT_AREA.toString())));
    }
    
    @Test
    @Transactional
    public void getTheatre() throws Exception {
        // Initialize the database
        theatreRepository.saveAndFlush(theatre);

        // Get the theatre
        restTheatreMockMvc.perform(get("/api/theatres/{id}", theatre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(theatre.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.area").value(DEFAULT_AREA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTheatre() throws Exception {
        // Get the theatre
        restTheatreMockMvc.perform(get("/api/theatres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTheatre() throws Exception {
        // Initialize the database
        theatreRepository.saveAndFlush(theatre);

        int databaseSizeBeforeUpdate = theatreRepository.findAll().size();

        // Update the theatre
        Theatre updatedTheatre = theatreRepository.findById(theatre.getId()).get();
        // Disconnect from session so that the updates on updatedTheatre are not directly saved in db
        em.detach(updatedTheatre);
        updatedTheatre
            .name(UPDATED_NAME)
            .area(UPDATED_AREA);
        TheatreDTO theatreDTO = theatreMapper.toDto(updatedTheatre);

        restTheatreMockMvc.perform(put("/api/theatres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(theatreDTO)))
            .andExpect(status().isOk());

        // Validate the Theatre in the database
        List<Theatre> theatreList = theatreRepository.findAll();
        assertThat(theatreList).hasSize(databaseSizeBeforeUpdate);
        Theatre testTheatre = theatreList.get(theatreList.size() - 1);
        assertThat(testTheatre.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTheatre.getArea()).isEqualTo(UPDATED_AREA);
    }

    @Test
    @Transactional
    public void updateNonExistingTheatre() throws Exception {
        int databaseSizeBeforeUpdate = theatreRepository.findAll().size();

        // Create the Theatre
        TheatreDTO theatreDTO = theatreMapper.toDto(theatre);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTheatreMockMvc.perform(put("/api/theatres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(theatreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Theatre in the database
        List<Theatre> theatreList = theatreRepository.findAll();
        assertThat(theatreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTheatre() throws Exception {
        // Initialize the database
        theatreRepository.saveAndFlush(theatre);

        int databaseSizeBeforeDelete = theatreRepository.findAll().size();

        // Get the theatre
        restTheatreMockMvc.perform(delete("/api/theatres/{id}", theatre.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Theatre> theatreList = theatreRepository.findAll();
        assertThat(theatreList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Theatre.class);
        Theatre theatre1 = new Theatre();
        theatre1.setId(1L);
        Theatre theatre2 = new Theatre();
        theatre2.setId(theatre1.getId());
        assertThat(theatre1).isEqualTo(theatre2);
        theatre2.setId(2L);
        assertThat(theatre1).isNotEqualTo(theatre2);
        theatre1.setId(null);
        assertThat(theatre1).isNotEqualTo(theatre2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TheatreDTO.class);
        TheatreDTO theatreDTO1 = new TheatreDTO();
        theatreDTO1.setId(1L);
        TheatreDTO theatreDTO2 = new TheatreDTO();
        assertThat(theatreDTO1).isNotEqualTo(theatreDTO2);
        theatreDTO2.setId(theatreDTO1.getId());
        assertThat(theatreDTO1).isEqualTo(theatreDTO2);
        theatreDTO2.setId(2L);
        assertThat(theatreDTO1).isNotEqualTo(theatreDTO2);
        theatreDTO1.setId(null);
        assertThat(theatreDTO1).isNotEqualTo(theatreDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(theatreMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(theatreMapper.fromId(null)).isNull();
    }
}
