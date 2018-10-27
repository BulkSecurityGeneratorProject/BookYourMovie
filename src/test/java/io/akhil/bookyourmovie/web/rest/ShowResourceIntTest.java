package io.akhil.bookyourmovie.web.rest;

import io.akhil.bookyourmovie.BookYourMovieApp;

import io.akhil.bookyourmovie.domain.Show;
import io.akhil.bookyourmovie.repository.ShowRepository;
import io.akhil.bookyourmovie.service.ShowService;
import io.akhil.bookyourmovie.service.dto.ShowDTO;
import io.akhil.bookyourmovie.service.mapper.ShowMapper;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static io.akhil.bookyourmovie.web.rest.TestUtil.sameInstant;
import static io.akhil.bookyourmovie.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ShowResource REST controller.
 *
 * @see ShowResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookYourMovieApp.class)
public class ShowResourceIntTest {

    private static final ZonedDateTime DEFAULT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ShowMapper showMapper;
    
    @Autowired
    private ShowService showService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restShowMockMvc;

    private Show show;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShowResource showResource = new ShowResource(showService);
        this.restShowMockMvc = MockMvcBuilders.standaloneSetup(showResource)
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
    public static Show createEntity(EntityManager em) {
        Show show = new Show()
            .time(DEFAULT_TIME);
        return show;
    }

    @Before
    public void initTest() {
        show = createEntity(em);
    }

    @Test
    @Transactional
    public void createShow() throws Exception {
        int databaseSizeBeforeCreate = showRepository.findAll().size();

        // Create the Show
        ShowDTO showDTO = showMapper.toDto(show);
        restShowMockMvc.perform(post("/api/shows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(showDTO)))
            .andExpect(status().isCreated());

        // Validate the Show in the database
        List<Show> showList = showRepository.findAll();
        assertThat(showList).hasSize(databaseSizeBeforeCreate + 1);
        Show testShow = showList.get(showList.size() - 1);
        assertThat(testShow.getTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void createShowWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = showRepository.findAll().size();

        // Create the Show with an existing ID
        show.setId(1L);
        ShowDTO showDTO = showMapper.toDto(show);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShowMockMvc.perform(post("/api/shows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(showDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Show in the database
        List<Show> showList = showRepository.findAll();
        assertThat(showList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllShows() throws Exception {
        // Initialize the database
        showRepository.saveAndFlush(show);

        // Get all the showList
        restShowMockMvc.perform(get("/api/shows?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(show.getId().intValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(sameInstant(DEFAULT_TIME))));
    }
    
    @Test
    @Transactional
    public void getShow() throws Exception {
        // Initialize the database
        showRepository.saveAndFlush(show);

        // Get the show
        restShowMockMvc.perform(get("/api/shows/{id}", show.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(show.getId().intValue()))
            .andExpect(jsonPath("$.time").value(sameInstant(DEFAULT_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingShow() throws Exception {
        // Get the show
        restShowMockMvc.perform(get("/api/shows/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShow() throws Exception {
        // Initialize the database
        showRepository.saveAndFlush(show);

        int databaseSizeBeforeUpdate = showRepository.findAll().size();

        // Update the show
        Show updatedShow = showRepository.findById(show.getId()).get();
        // Disconnect from session so that the updates on updatedShow are not directly saved in db
        em.detach(updatedShow);
        updatedShow
            .time(UPDATED_TIME);
        ShowDTO showDTO = showMapper.toDto(updatedShow);

        restShowMockMvc.perform(put("/api/shows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(showDTO)))
            .andExpect(status().isOk());

        // Validate the Show in the database
        List<Show> showList = showRepository.findAll();
        assertThat(showList).hasSize(databaseSizeBeforeUpdate);
        Show testShow = showList.get(showList.size() - 1);
        assertThat(testShow.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingShow() throws Exception {
        int databaseSizeBeforeUpdate = showRepository.findAll().size();

        // Create the Show
        ShowDTO showDTO = showMapper.toDto(show);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShowMockMvc.perform(put("/api/shows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(showDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Show in the database
        List<Show> showList = showRepository.findAll();
        assertThat(showList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShow() throws Exception {
        // Initialize the database
        showRepository.saveAndFlush(show);

        int databaseSizeBeforeDelete = showRepository.findAll().size();

        // Get the show
        restShowMockMvc.perform(delete("/api/shows/{id}", show.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Show> showList = showRepository.findAll();
        assertThat(showList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Show.class);
        Show show1 = new Show();
        show1.setId(1L);
        Show show2 = new Show();
        show2.setId(show1.getId());
        assertThat(show1).isEqualTo(show2);
        show2.setId(2L);
        assertThat(show1).isNotEqualTo(show2);
        show1.setId(null);
        assertThat(show1).isNotEqualTo(show2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShowDTO.class);
        ShowDTO showDTO1 = new ShowDTO();
        showDTO1.setId(1L);
        ShowDTO showDTO2 = new ShowDTO();
        assertThat(showDTO1).isNotEqualTo(showDTO2);
        showDTO2.setId(showDTO1.getId());
        assertThat(showDTO1).isEqualTo(showDTO2);
        showDTO2.setId(2L);
        assertThat(showDTO1).isNotEqualTo(showDTO2);
        showDTO1.setId(null);
        assertThat(showDTO1).isNotEqualTo(showDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(showMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(showMapper.fromId(null)).isNull();
    }
}
