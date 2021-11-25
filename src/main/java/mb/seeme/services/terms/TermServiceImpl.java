package mb.seeme.services.terms;

import mb.seeme.model.terms.Term;
import mb.seeme.repositories.TermRepository;
import java.util.HashSet;
import java.util.Set;

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
}
