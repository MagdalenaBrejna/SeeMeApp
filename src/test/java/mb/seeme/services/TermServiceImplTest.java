package mb.seeme.services;

import mb.seeme.model.terms.Term;
import mb.seeme.repositories.TermRepository;
import mb.seeme.services.terms.TermServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TermServiceImplTest {

    @Mock
    TermRepository termRepository;

    @InjectMocks
    TermServiceImpl service;

    Term term;

    @BeforeEach
    void setUp() {
        term = Term.builder().id(2l).build();
    }

    @Test
    void findAll() {
        //given
        Set<Term> testTermSet = new HashSet<>();
        testTermSet.add(Term.builder().id(1l).build());
        testTermSet.add(Term.builder().id(2l).build());
        //when
        when(termRepository.findAll()).thenReturn(testTermSet);
        Set<Term> termSet = service.findAll();
        //then
        assertNotNull(termSet);
        assertEquals(2, termSet.size());
    }

    @Test
    void findById() {
        //when
        when(termRepository.findById(anyLong())).thenReturn(Optional.of(term));
        Term testTerm = service.findById(2l);
        //then
        assertNotNull(testTerm);
    }

    @Test
    void findByIdNotFound() {
        //when
        when(termRepository.findById(anyLong())).thenReturn(Optional.empty());
        Term testTerm = service.findById(2l);
        //then
        assertNull(testTerm);
    }

    @Test
    void save() {
        //given
        Term termToSave = Term.builder().id(1l).build();
        //when
        when(termRepository.save(any())).thenReturn(termToSave);
        Term savedTerm = service.save(termToSave);
        //then
        assertNotNull(savedTerm);
        verify(termRepository).save(any());
    }

    @Test
    void delete() {
        //when
        service.delete(term);
        //then
        verify(termRepository).delete(any());
    }

    @Test
    void deleteById() {
        //when
        service.deleteById(2l);
        //then
        verify(termRepository).deleteById(anyLong());
    }
}