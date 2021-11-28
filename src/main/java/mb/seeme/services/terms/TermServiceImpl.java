package mb.seeme.services.terms;

import mb.seeme.model.terms.Term;
import mb.seeme.repositories.TermRepository;

import javax.transaction.Transactional;
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
    @Transactional
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
                .filter(term -> term.getTermDate().isAfter(LocalDate.now()) || term.getTermDate().isEqual(LocalDate.now()))
                .collect(Collectors.toList());
        return terms;
    }

    @Override
    public List<Term> findAllPastByClientId(Long id) {
        List<Term> terms = findAllByClientId(id).stream()
                .filter(term -> term.getTermDate().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
        return terms;
    }

    private List<Term> findAllByClientId(Long id) {
        return termRepository.findAllByClientId(id).stream()
                .sorted((term1, term2) -> term1.getTermDate().compareTo(term2.getTermDate()))
                .collect(Collectors.toList());
    }


    @Override
    public List<Term> findAllFutureFreeByProviderId(Long id) {
        return findFreeTermsByProviderIdFromDate(id, LocalDate.now());
    }

    @Override
    public List<Term> findAllFutureFreeByProviderIdFromDate(Long id, LocalDate date) {
        if(date.isBefore(LocalDate.now()))
            date = LocalDate.now();
        return findFreeTermsByProviderIdFromDate(id, date);
    }

    private List<Term> findFreeTermsByProviderIdFromDate(Long id, LocalDate date) {
        return termRepository.findAllFreeByProviderIdFromDate(id, date).stream()
                .sorted((term1, term2) -> term1.getTermDate().compareTo(term2.getTermDate()))
                .collect(Collectors.toList());
    }


    @Override
    public List<Term> findAllFutureAppointedByProviderId(Long id) {
        return findAppointedTermsByProviderIdFromDate(id, LocalDate.now());
    }

    @Override
    public List<Term> findAllFutureAppointedByProviderIdFromDate(Long id, LocalDate date) {
        if(date.isBefore(LocalDate.now()))
            date = LocalDate.now();
        return findAppointedTermsByProviderIdFromDate(id, date);
    }

    private List<Term> findAppointedTermsByProviderIdFromDate(Long id, LocalDate date) {
        return termRepository.findAllAppointedByProviderIdFromDate(id, date).stream()
                .sorted((term1, term2) -> term1.getTermDate().compareTo(term2.getTermDate()))
                .collect(Collectors.toList());
    }
}
