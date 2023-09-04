package com.driver.services;


import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.repository.SubscriptionRepository;
import com.driver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    UserRepository userRepository;

    public Integer buySubscription(SubscriptionEntryDto subscriptionEntryDto){

        //Save The subscription Object into the Db and return the total Amount that user has to pay
        Subscription subscription = new Subscription();
        //@Id
        //    @GeneratedValue(strategy = GenerationType.IDENTITY)
        //    private int id;
        //
        //    private SubscriptionType subscriptionType;
        //
        //    private int noOfScreensSubscribed;
        //
        //    private Date startSubscriptionDate;
        //
        //    private int totalAmountPaid;
        //
        //    @OneToOne
        //    @JoinColumn
        //    private User user;

        SubscriptionType subscriptionType  = subscriptionEntryDto.getSubscriptionType();
        int totalcosttobepaid = 0;
        if(subscriptionType == SubscriptionType.BASIC){
            totalcosttobepaid = 500+(200* subscriptionEntryDto.getNoOfScreensRequired());
        }else if(subscriptionType == SubscriptionType.PRO){
            totalcosttobepaid = 800+(250* subscriptionEntryDto.getNoOfScreensRequired());
        }else if(subscriptionType == SubscriptionType.ELITE){
            totalcosttobepaid = 1000+(350* subscriptionEntryDto.getNoOfScreensRequired());
        }
        subscription.setTotalAmountPaid(totalcosttobepaid);
        subscription.setSubscriptionType(subscriptionType);

        //get currentdate
        Date currentdate = new Date();
        subscription.setStartSubscriptionDate(currentdate);
        User user = userRepository.findById(subscriptionEntryDto.getUserId()).get();
        user.setSubscription(subscription);
        userRepository.save(user);
        subscriptionRepository.save(subscription);
        return totalcosttobepaid;
    }

    public Integer upgradeSubscription(Integer userId)throws Exception{

        //If you are already at an ElITE subscription : then throw Exception ("Already the best Subscription")
        //In all other cases just try to upgrade the subscription and tell the difference of price that user has to pay
        //update the subscription in the repository



        //In this function you need to upgrade the subscription to  its next level
        //ie if You are A BASIC subscriber update to PRO and if You are a PRO upgrade to ELITE.
        //Incase you are already an ELITE member throw an Exception
        //and at the end return the difference in fare that you need to pay to get this subscription done.

         User user = userRepository.findById(userId).get();
         SubscriptionType subType = user.getSubscription().getSubscriptionType();
        Subscription subscription = user.getSubscription();
        int noofScreenRequired = subscription.getNoOfScreensSubscribed();
        int diiftoUpgrade = 0;

         if(subType == SubscriptionType.ELITE){
             throw  new Exception("Already the best Subscription");
         }else if(subType == SubscriptionType.BASIC){
             subscription.setSubscriptionType(SubscriptionType.PRO);
             diiftoUpgrade = (800+(250*noofScreenRequired))-subscription.getTotalAmountPaid();
         }else if(subType == SubscriptionType.PRO){
             subscription.setSubscriptionType(SubscriptionType.ELITE);
             diiftoUpgrade = (1000+(350*noofScreenRequired))-subscription.getTotalAmountPaid();
         }

         user.setSubscription(subscription);
         userRepository.save(user);
         return diiftoUpgrade;
    }

    public Integer calculateTotalRevenueOfHotstar(){

        //We need to find out total Revenue of hotstar : from all the subscriptions combined
        //Hint is to use findAll function from the SubscriptionDb
        List<Subscription>subscriptions =  subscriptionRepository.findAll();
        int totalRevernue = 0;
        for(Subscription sub:subscriptions){
            totalRevernue+=sub.getTotalAmountPaid();
        }

        return totalRevernue;
    }

}
