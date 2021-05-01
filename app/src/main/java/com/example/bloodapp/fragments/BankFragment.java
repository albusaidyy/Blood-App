package com.example.bloodapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodapp.BankDetailsActivity;
import com.example.bloodapp.HomeActivity;
import com.example.bloodapp.R;
import com.example.bloodapp.model.BankStock;
import com.example.bloodapp.sqlite.DatabaseHelper;

import java.util.ArrayList;

public class BankFragment extends Fragment {

    //variable declaration
    View view;
    DatabaseHelper database_helper;
    RecyclerView recyclerView;
    BankAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bank_fragment, container, false);
        recyclerView = view.findViewById(R.id.bank_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        database_helper = new DatabaseHelper(getContext());
        //call load all banks method
        loadBanks();
        return view;
    }


    private void loadBanks() {
        ArrayList<BankStock> allBanks = new ArrayList<>();
        allBanks.clear();
        //call allBanks method and return list of Banks
        if (allBanks != null) {
            allBanks = database_helper.getAllBanks();
            adapter = new BankAdapter(allBanks);
            recyclerView.setAdapter(adapter);
        }

    }

    private void searchBanks(String keyword) {
        ArrayList<BankStock> filteredBanks = new ArrayList<>();
        filteredBanks.clear();
        if (filteredBanks != null) {
            //call searchBanks method and return list of the banks
            filteredBanks = database_helper.searchBanks(keyword);
            adapter = new BankAdapter(filteredBanks);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    //populate the menu items
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = new SearchView(((HomeActivity) getContext()).getSupportActionBar().getThemedContext());
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setActionView(searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //called when user press search icon and submits
                if (!TextUtils.isEmpty(query)) {
                    searchBanks(query);
                } else {
                    loadBanks();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //called when user press search icon and types
                if (!TextUtils.isEmpty(newText)) {
                    searchBanks(newText);
                } else {
                    loadBanks();
                }
                return false;
            }
        });
    }


    public class BankAdapter extends RecyclerView.Adapter<BankAdapter.viewHolder> {

        ArrayList<BankStock> allBanks;

        public BankAdapter(ArrayList<BankStock> allBanks) {
            this.allBanks = allBanks;
        }

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.bank_item, viewGroup, false);
            return new viewHolder(view);
        }

        @Override
        public void onBindViewHolder(viewHolder viewHolder, int position) {


            //variable declaration and assigning values from list
            String id = String.valueOf(allBanks.get(position).getId());
            String name = allBanks.get(position).getName();
            String email = allBanks.get(position).getEmail();
            String address = allBanks.get(position).getAddress();
            String contact = allBanks.get(position).getContact();

            int bloodGroupAP = allBanks.get(position).getBloodTypeAP();
            int bloodGroupAN = allBanks.get(position).getBloodTypeAN();

            int bloodGroupBP = allBanks.get(position).getBloodTypeBP();
            int bloodGroupBN = allBanks.get(position).getBloodTypeBN();

            int bloodGroupABP = allBanks.get(position).getBloodTypeABP();
            int bloodGroupABN = allBanks.get(position).getBloodTypeABN();

            int bloodGroupOP = allBanks.get(position).getBloodTypeOP();
            int bloodGroupON = allBanks.get(position).getBloodTypeON();

            //set values to TextViews
            viewHolder.bName.setText(name);
            viewHolder.bEmail.setText(email);
            viewHolder.bAddress.setText(address);


            viewHolder.BViewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //puts the details of the selected item and send them to the next screen to be received
                    Intent intent = new Intent(getContext(), BankDetailsActivity.class);
                    intent.putExtra("bId", id);
                    intent.putExtra("bName", name);
                    intent.putExtra("bEmail", email);
                    intent.putExtra("bAddress", address);
                    intent.putExtra("bContact", contact);
                    intent.putExtra("bBloodAP", String.valueOf(bloodGroupAP));
                    intent.putExtra("bBloodAN", String.valueOf(bloodGroupAN));
                    intent.putExtra("bBloodBP", String.valueOf(bloodGroupBP));
                    intent.putExtra("bBloodBN", String.valueOf(bloodGroupBN));
                    intent.putExtra("bBloodABP", String.valueOf(bloodGroupABP));
                    intent.putExtra("bBloodABN", String.valueOf(bloodGroupABN));
                    intent.putExtra("bBloodOP", String.valueOf(bloodGroupOP));
                    intent.putExtra("bBloodON", String.valueOf(bloodGroupON));
                    requireContext().startActivity(intent);

                }
            });


        }

        @Override
        public int getItemCount() {
            return allBanks.size();
        }

        public class viewHolder extends RecyclerView.ViewHolder {
            TextView bName, bAddress, bEmail;
            Button BViewDetails;

            public viewHolder(View itemView) {
                super(itemView);
                //link Views to xml layout
                bName = itemView.findViewById(R.id.b_name);
                bAddress = itemView.findViewById(R.id.b_address);
                bEmail = itemView.findViewById(R.id.b_email);
                BViewDetails = itemView.findViewById(R.id.b_viewDetails);
            }
        }
    }
}