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
    public Term findById(Long termId) {
        return termRepository.findById(termId).orElse(null);
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
    public void deleteById(Long termId) {
        termRepository.deleteById(termId);
    }


    @Override
    public List<Term> findAllFutureByClientId(Long clientId) {
        List<Term> terms = findAllByClientId(clientId).stream()
                .filter(term -> term.getTermDate().isAfter(LocalDate.now()) || term.getTermDate().isEqual(LocalDate.now()))
                .collect(Collectors.toList());
        return terms;
    }

    @Override
    public List<Term> findAllPastByClientId(Long clientId) {
        List<Term> terms = findAllByClientId(clientId).stream()
                .filter(term -> term.getTermDate().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
        return terms;
    }

    private List<Term> findAllByClientId(Long clientId) {
        return termRepository.findAllByClientId(clientId).stream()
                .sorted((term1, term2) -> term1.getTermDate().compareTo(term2.getTermDate()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Term> findAllFutureByProviderId(Long providerId) {
        return termRepository.findAllFutureByProviderId(providerId, LocalDate.now());
    }

    @Override
    public List<Term> findAllFutureFreeByProviderId(Long providerId) {
        return findFreeTermsByProviderIdFromDate(providerId, LocalDate.now());
    }

    @Override
    public List<Term> findAllFutureFreeByProviderIdFromDate(Long providerId, LocalDate selectedDate) {
        if(selectedDate.isBefore(LocalDate.now()))
            selectedDate = LocalDate.now();
        return findFreeTermsByProviderIdFromDate(providerId, selectedDate);
    }

    private List<Term> findFreeTermsByProviderIdFromDate(Long providerId, LocalDate selectedDate) {
        return termRepository.findAllFreeByProviderIdFromDate(providerId, selectedDate).stream()
                .sorted((term1, term2) -> term1.getTermDate().compareTo(term2.getTermDate()))
                .collect(Collectors.toList());
    }


    @Override
    public List<Term> findAllFutureAppointedByProviderId(Long providerId) {
        return findAppointedTermsByProviderIdFromDate(providerId, LocalDate.now());
    }

    @Override
    public List<Term> findAllFutureAppointedByProviderIdFromDate(Long providerId, LocalDate selectedDate) {
        if(selectedDate.isBefore(LocalDate.now()))
            selectedDate = LocalDate.now();
        return findAppointedTermsByProviderIdFromDate(providerId, selectedDate);
    }

    private List<Term> findAppointedTermsByProviderIdFromDate(Long providerId, LocalDate selectedDate) {
        return termRepository.findAllAppointedByProviderIdFromDate(providerId, selectedDate).stream()
                .sorted((term1, term2) -> term1.getTermDate().compareTo(term2.getTermDate()))
                .collect(Collectors.toList());
    }


    @Override
    public List<Term> findAllPastAppointedByProviderId(Long providerId) {
        List<Term> terms = termRepository.findAllAppointedByProviderIdBeforeDate(providerId, LocalDate.now())
                .stream()
                .sorted((term1, term2) -> term2.getTermDate().compareTo(term1.getTermDate()))
                .collect(Collectors.toList());
        return terms;
    }

    @Override
    public List<Term> findAllPastAppointedByDateAndProviderId(Long providerId, LocalDate selectedDate) {
        if(selectedDate.isAfter(LocalDate.now()))
            selectedDate = LocalDate.now();
        List<Term> terms = termRepository.findAllAppointedByProviderIdAtDate(providerId, selectedDate)
                .stream()
                .sorted((term1, term2) -> term1.getTermTime().compareTo(term2.getTermTime()))
                .collect(Collectors.toList());
        return terms;
    }

    @Override
    public List<Term> findAllPastAppointedByProviderIdAndClientId(Long providerId, Long clientId) {
        List<Term> terms = findAllPastByClientId(clientId)
                .stream()
                .filter(term -> term.getService().getServiceProvider().getId() == providerId)
                .sorted((term1, term2) -> term2.getTermDate().compareTo(term1.getTermDate()))
                .collect(Collectors.toList());
        return terms;
    }

    @Override
    public List<Term> findAllPastAppointedByDateAndProviderIdAndClientId(Long providerId, Long clientId, LocalDate selectedDate) {
        List<Term> terms = findAllPastByClientId(clientId)
                .stream()
                .filter(term -> (term.getTermDate().equals(selectedDate) && term.getService().getServiceProvider().getId() == providerId))
                .sorted((term1, term2) -> term1.getTermTime().compareTo(term2.getTermTime()))
                .collect(Collectors.toList());
        return terms;
    }
}
