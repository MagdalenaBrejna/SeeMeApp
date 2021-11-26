package mb.seeme.services.terms;

import mb.seeme.model.terms.Term;
import mb.seeme.repositories.TermRepository;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TermServiceImpl implements TermService {

    private final TermRepository termRepository;

    public TermServiceImpl(TermRepository termRepository) {
        this.termRepository = termRepository;
    }

    @Override
    public Set<Term> findAll() {
        Set<Term> terms = new HashSet<>();
        termRepository.findAll().forEach(terms::add);
        return terms;
    }

    @Override
    public Term findById(Long id) {
        return termRepository.findById(id).orElse(null);
    }

    @Override
    public Term save(Term term) {
        return termRepository.save(term);
    }

    @Override
    public void delete(Term term) {
        termRepository.delete(term);
    }

    @Override
    public void deleteById(Long id) {
        termRepository.deleteById(id);
    }

    @Override
    public List<Term> findAllFutureByClientId(Long id) {
        List<Term> terms = findAllByClientId(id).stream()
                .filter(term -> term.getDate().isAfter(LocalDate.now()) || term.getDate().isEqual(LocalDate.now()))
                .collect(Collectors.toList());
        return terms;
    }

    @Override
    public List<Term> findAllPastByClientId(Long id) {
        List<Term> terms = findAllByClientId(id).stream()
                .filter(term -> term.getDate().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
        return terms;
    }

    private List<Term> findAllByClientId(Long id) {
        return termRepository.findAllByClientId(id).stream()
                .sorted((term1, term2) -> term1.getDate().compareTo(term2.getDate()))
                .collect(Collectors.toList());
    }
}
