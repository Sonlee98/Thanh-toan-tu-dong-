import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialog } from '@angular/material/dialog';
import { SubscriptionService } from '../../../services/subscription.service';
import { PaymentService } from '../../../services/payment.service';
import { PaymentDialogComponent } from '../../../components/payment-dialog/payment-dialog.component';

interface SubscriptionDto {
  planCode?: string;
  expirationDate?: string;
  status?: string;
  price?: number;
  startDate?: string;
  subscriber?: string;
}

interface PlanDto {
  code: string;
  name: string;
  price: number;
  description: string;
  status: string;
  duration: number;
}

@Component({
  selector: 'app-manage-subscription',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatButtonModule, MatIconModule],
  templateUrl: './manage-subscription.component.html',
  styleUrl: './manage-subscription.component.scss'
})
export class ManageSubscriptionComponent implements OnInit {
  private readonly subscriptionService = inject(SubscriptionService);
  private readonly paymentService = inject(PaymentService);
  private readonly dialog = inject(MatDialog);

  subscriberId = 'current-user@example.com';

  current = signal<SubscriptionDto | null>(null);
  plans = signal<PlanDto[]>([]);

  remainingDays = computed(() => {
    const cur = this.current();
    if (!cur?.expirationDate) return null;
    const diff = Math.ceil((new Date(cur.expirationDate).getTime() - Date.now()) / (1000 * 60 * 60 * 24));
    return diff;
  });

  ngOnInit(): void {
    this.loadData();
  }

  private loadData(): void {
    this.subscriptionService.getCurrent(this.subscriberId).subscribe((res) => this.current(res || null));
    this.subscriptionService.getPlans().subscribe((list) => this.plans(list));
  }

  isActivePlan(planCode: string): boolean {
    return (this.current()?.planCode || '') === planCode && (this.current()?.status || '') === 'ACTIVE';
  }

  upgrade(plan: PlanDto): void {
    this.paymentService.getPaymentImages('payment').subscribe((images) => {
      const ref = this.dialog.open(PaymentDialogComponent, {
        data: { images, plan, subscriber: this.subscriberId },
        width: '720px'
      });
      ref.afterClosed().subscribe((confirmed) => {
        if (confirmed) {
          this.subscriptionService.upgrade({ planCode: plan.code, subscriber: this.subscriberId }).subscribe(() => this.loadData());
        }
      });
    });
  }
}
