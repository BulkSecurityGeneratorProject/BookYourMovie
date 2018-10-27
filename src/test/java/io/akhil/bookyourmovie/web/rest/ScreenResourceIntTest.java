package io.akhil.bookyourmovie.web.rest;

import io.akhil.bookyourmovie.BookYourMovieApp;

import io.akhil.bookyourmovie.domain.Screen;
import io.akhil.bookyourmovie.repository.ScreenRepository;
import io.akhil.bookyourmovie.service.ScreenService;
import io.akhil.bookyourmovie.service.dto.ScreenDTO;
import io.akhil.bookyourmovie.service.mapper.ScreenMapper;
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
 * Test class for the ScreenResource REST controller.
 *
 * @see ScreenResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookYourMovieApp.class)
public class ScreenResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private ScreenMapper screenMapper;
    
    @Autowired
    private ScreenService screenService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restScreenMockMvc;

    private Screen screen;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ScreenResource screenResource = new ScreenResource(screenService);
        this.restScreenMockMvc = MockMvcBuilders.standaloneSetup(screenResource)
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
    public static Screen createEntity(EntityManager em) {
        Screen screen = new Screen()
            .name(DEFAULT_NAME);
        return screen;
    }

    @Before
    public void initTest() {
        screen = createEntity(em);
    }

    @Test
    @Transactional
    public void createScreen() throws Exception {
        int databaseSizeBeforeCreate = screenRepository.findAll().size();

        // Create the Screen
        ScreenDTO screenDTO = screenMapper.toDto(screen);
        restScreenMockMvc.perform(post("/api/screens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(screenDTO)))
            .andExpect(status().isCreated());

        // Validate the Screen in the database
        List<Screen> screenList = screenRepository.findAll();
        assertThat(screenList).hasSize(databaseSizeBeforeCreate + 1);
        Screen testScreen = screenList.get(screenList.size() - 1);
        assertThat(testScreen.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createScreenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = screenRepository.findAll().size();

        // Create the Screen with an existing ID
        screen.setId(1L);
        ScreenDTO screenDTO = screenMapper.toDto(screen);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScreenMockMvc.perform(post("/api/screens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(screenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Screen in the database
        List<Screen> screenList = screenRepository.findAll();
        assertThat(screenList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllScreens() throws Exception {
        // Initialize the database
        screenRepository.saveAndFlush(screen);

        // Get all the screenList
        restScreenMockMvc.perform(get("/api/screens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(screen.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getScreen() throws Exception {
        // Initialize the database
        screenRepository.saveAndFlush(screen);

        // Get the screen
        restScreenMockMvc.perform(get("/api/screens/{id}", screen.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(screen.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingScreen() throws Exception {
        // Get the screen
        restScreenMockMvc.perform(get("/api/screens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScreen() throws Exception {
        // Initialize the database
        screenRepository.saveAndFlush(screen);

        int databaseSizeBeforeUpdate = screenRepository.findAll().size();

        // Update the screen
        Screen updatedScreen = screenRepository.findById(screen.getId()).get();
        // Disconnect from session so that the updates on updatedScreen are not directly saved in db
        em.detach(updatedScreen);
        updatedScreen
            .name(UPDATED_NAME);
        ScreenDTO screenDTO = screenMapper.toDto(updatedScreen);

        restScreenMockMvc.perform(put("/api/screens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(screenDTO)))
            .andExpect(status().isOk());

        // Validate the Screen in the database
        List<Screen> screenList = screenRepository.findAll();
        assertThat(screenList).hasSize(databaseSizeBeforeUpdate);
        Screen testScreen = screenList.get(screenList.size() - 1);
        assertThat(testScreen.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingScreen() throws Exception {
        int databaseSizeBeforeUpdate = screenRepository.findAll().size();

        // Create the Screen
        ScreenDTO screenDTO = screenMapper.toDto(screen);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScreenMockMvc.perform(put("/api/screens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(screenDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Screen in the database
        List<Screen> screenList = screenRepository.findAll();
        assertThat(screenList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteScreen() throws Exception {
        // Initialize the database
        screenRepository.saveAndFlush(screen);

        int databaseSizeBeforeDelete = screenRepository.findAll().size();

        // Get the screen
        restScreenMockMvc.perform(delete("/api/screens/{id}", screen.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Screen> screenList = screenRepository.findAll();
        assertThat(screenList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Screen.class);
        Screen screen1 = new Screen();
        screen1.setId(1L);
        Screen screen2 = new Screen();
        screen2.setId(screen1.getId());
        assertThat(screen1).isEqualTo(screen2);
        screen2.setId(2L);
        assertThat(screen1).isNotEqualTo(screen2);
        screen1.setId(null);
        assertThat(screen1).isNotEqualTo(screen2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScreenDTO.class);
        ScreenDTO screenDTO1 = new ScreenDTO();
        screenDTO1.setId(1L);
        ScreenDTO screenDTO2 = new ScreenDTO();
        assertThat(screenDTO1).isNotEqualTo(screenDTO2);
        screenDTO2.setId(screenDTO1.getId());
        assertThat(screenDTO1).isEqualTo(screenDTO2);
        screenDTO2.setId(2L);
        assertThat(screenDTO1).isNotEqualTo(screenDTO2);
        screenDTO1.setId(null);
        assertThat(screenDTO1).isNotEqualTo(screenDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(screenMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(screenMapper.fromId(null)).isNull();
    }
}
