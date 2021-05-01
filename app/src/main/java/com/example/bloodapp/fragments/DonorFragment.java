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

import com.example.bloodapp.DonorDetailsActivity;
import com.example.bloodapp.HomeActivity;
import com.example.bloodapp.R;
import com.example.bloodapp.model.User;
import com.example.bloodapp.sqlite.DatabaseHelper;

import java.util.ArrayList;

public class DonorFragment extends Fragment {

    //variable declaration
    View view;
    DatabaseHelper database_helper;

    RecyclerView recyclerView;
    DonorAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.donor_fragment, container, false);
        recyclerView = view.findViewById(R.id.donor_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        database_helper = new DatabaseHelper(getContext());

        //call method to load all Donors
        loadDonors();
        return view;


    }

    //call method getAllDonors and returns a list of Donors
    private void loadDonors() {
        ArrayList<User> DonorArrayList = new ArrayList<>();
        DonorArrayList.clear();
        if (DonorArrayList != null) {
            DonorArrayList = database_helper.getAllDonors();
            adapter = new DonorAdapter(DonorArrayList);
            recyclerView.setAdapter(adapter);
        }

    }


    //call method searchDonor and return a list of filtered Donors
    private void searchDonor(String keyword) {
        ArrayList<User> filteredDonorArrayList = new ArrayList<>();
        filteredDonorArrayList.clear();
        if (filteredDonorArrayList != null) {
            filteredDonorArrayList = database_helper.searchDonor(keyword);
            adapter = new DonorAdapter(filteredDonorArrayList);
            recyclerView.setAdapter(adapter);
        }


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    //assign and populate menu items
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
                    searchDonor(query);
                } else {
                    loadDonors();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //called when user press search icon continues to type
                if (!TextUtils.isEmpty(newText)) {
                    searchDonor(newText);
                } else {
                    loadDonors();
                }
                return false;
            }
        });

    }


    public class DonorAdapter extends RecyclerView.Adapter<DonorAdapter.viewHolder> {

        ArrayList<User> userDonorArrayList;

        public DonorAdapter(ArrayList<User> userDonorArrayList) {
            this.userDonorArrayList = userDonorArrayList;
        }

        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.donor_item, viewGroup, false);
            return new viewHolder(view);

        }

        @Override
        public void onBindViewHolder(viewHolder viewHolder, int position) {

            //variables declaration and assignment
            String name = (userDonorArrayList.get(position).getName());
            String email = (userDonorArrayList.get(position).getEmail());
            String gender = (userDonorArrayList.get(position).getGender());
            String address = (userDonorArrayList.get(position).getAddress());
            String contact = (userDonorArrayList.get(position).getContact());
            String blood_group = (userDonorArrayList.get(position).getBloodType());
            String status = (userDonorArrayList.get(position).getStatus());

            //set text from list to TextViews
            viewHolder.dName.setText(name);
            viewHolder.dGender.setText(gender);
            viewHolder.dAddress.setText(address);
            viewHolder.dBlood_group.setText(blood_group);


            viewHolder.DViewDetails.setOnClickListener(view -> {
                //puts the details of the selected item and send them to the next screen to be received
                Intent intent = new Intent(getContext(), DonorDetailsActivity.class);
                intent.putExtra("dName", name);
                intent.putExtra("dEmail", email);
                intent.putExtra("dGender", gender);
                intent.putExtra("dAddress", address);
                intent.putExtra("dContact", contact);
                intent.putExtra("dBloodGroup", blood_group);
                intent.putExtra("dStatus", status);
                requireContext().startActivity(intent);
            });

        }

        @Override
        public int getItemCount() {
            return userDonorArrayList.size();
        }

        public class viewHolder extends RecyclerView.ViewHolder {
            TextView dName, dAddress, dGender, dBlood_group;
            Button DViewDetails;

            public viewHolder(View itemView) {
                super(itemView);
                //link views to xml layout
                dName = itemView.findViewById(R.id.d_name);
                dGender = itemView.findViewById(R.id.d_Gender);
                dBlood_group = itemView.findViewById(R.id.d_BloodGroup);
                dAddress = itemView.findViewById(R.id.d_address);
                DViewDetails = itemView.findViewById(R.id.d_viewDetails);

            }
        }
    }


}